package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.GroupOrder;
import com.pixiehex.kshipping.repository.GroupOrderRepository;
import com.pixiehex.kshipping.services.BatchService;
import com.pixiehex.kshipping.services.PdfGeneratorService;
import com.pixiehex.kshipping.services.SingleOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
@CrossOrigin(origins = "") // Odblokowuje dostęp dla Reacta
public class BatchController {

    private final BatchService batchService;
    private final GroupOrderRepository groupOrderRepository;
    private final PdfGeneratorService pdfGeneratorService;
    private final SingleOrderService singleOrderService;

    public BatchController(BatchService batchService, GroupOrderRepository groupOrderRepository, PdfGeneratorService pdfGeneratorService, SingleOrderService singleOrderService) {
        this.batchService = batchService;
        this.groupOrderRepository = groupOrderRepository;
        this.pdfGeneratorService = pdfGeneratorService;
        this.singleOrderService = singleOrderService;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runBatching() {
        // To wywołuje Twój algorytm + generowanie PDF
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
                    // Generujemy plik na żądanie
                    pdfGeneratorService.generateShippingLabels(group);

                    // Automatycznie zmieniamy status na DELIVERED_TO_USERS? (Opcjonalnie)
                    // group.setStatus(GroupOrder.GroupStatus.DELIVERED_TO_USERS);
                    // groupOrderRepository.save(group);

                    return ResponseEntity.ok("Etykiety wygenerowane! Pobierz plik: LABELS_" + group.getName().replace(" ", "_") + ".pdf");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}