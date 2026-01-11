package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.GroupOrder;
import com.pixiehex.kshipping.model.SingleOrder; 
import com.pixiehex.kshipping.repository.GroupOrderRepository;
import com.pixiehex.kshipping.services.BatchService;
import com.pixiehex.kshipping.services.FakeEmailService;
import com.pixiehex.kshipping.services.PdfGeneratorService;
import com.pixiehex.kshipping.services.SingleOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
@CrossOrigin(origins = "*")
public class BatchController {

    private final BatchService batchService;
    private final GroupOrderRepository groupOrderRepository;
    private final PdfGeneratorService pdfGeneratorService;
    private final FakeEmailService emailService; 
    private final SingleOrderService singleOrderService;

    public BatchController(BatchService batchService,
                           GroupOrderRepository groupOrderRepository,
                           PdfGeneratorService pdfGeneratorService,
                           FakeEmailService emailService, SingleOrderService singleOrderService) {
        this.batchService = batchService;
        this.groupOrderRepository = groupOrderRepository;
        this.pdfGeneratorService = pdfGeneratorService;
        this.emailService = emailService;
        this.singleOrderService = singleOrderService;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runBatching() {
        singleOrderService.changeUnpaidToCancelled();
        String result = batchService.processBatching();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/groups")
    public ResponseEntity<List<GroupOrder>> getAllGroups() {
        return ResponseEntity.ok(groupOrderRepository.findAll());
    }

    @PostMapping("/{id}/labels")
    public ResponseEntity<String> generateLabels(@PathVariable Long id) {
        return groupOrderRepository.findById(id)
                .map(group -> {
                    pdfGeneratorService.generateShippingLabels(group);
                    return ResponseEntity.ok("Etykiety wygenerowane! Pobierz plik: LABELS_" + group.getName().replace(" ", "_") + ".pdf");
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateBatchStatus(@PathVariable Long id, @RequestParam String newStatus) {
        return groupOrderRepository.findById(id)
                .map(group -> {
                    try {
                        GroupOrder.GroupStatus status = GroupOrder.GroupStatus.valueOf(newStatus);
                        group.setStatus(status);

                        if (status == GroupOrder.GroupStatus.ON_THE_WAY) {
                            for (SingleOrder order : group.getOrders()) {
                                emailService.sendEmail(
                                        order.getUserEmail(),
                                        "Paczka wypłynęła z Korei!",
                                        "Twoje zamówienie (" + order.getProductName() + ") jest w drodze do Polski."
                                );
                            }
                        } else if (status == GroupOrder.GroupStatus.DELIVERED_TO_USERS) {
                            for (SingleOrder order : group.getOrders()) {
                                emailService.sendEmail(
                                        order.getUserEmail(),
                                        "Paczka doręczona!",
                                        "Dziękujemy za zakupy w K-Shipping. Miłego używania!"
                                );
                            }
                        }

                        groupOrderRepository.save(group);
                        return ResponseEntity.ok("Status zmieniony na: " + status + ". Powiadomienia wysłane.");

                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body("Błąd: Nieznany status '" + newStatus + "'");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}