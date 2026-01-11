package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.dto.CreateOrderRequest;
import com.pixiehex.kshipping.model.SingleOrder;
import com.pixiehex.kshipping.services.SingleOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/single_orders") // <--- ZMIANA: Krótszy, standardowy URL
@CrossOrigin(origins = "*")
public class SingleOrderController {

    private final SingleOrderService singleOrderService;

    public SingleOrderController(SingleOrderService singleOrderService) {
        this.singleOrderService = singleOrderService;
    }

    @GetMapping
    public ResponseEntity<List<SingleOrder>> getAllOrders() {
        return ResponseEntity.ok(singleOrderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<SingleOrder> createPreorder(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("productName");

        double price = Double.parseDouble(payload.get("price").toString());
        double weight = payload.containsKey("weight") ? Double.parseDouble(payload.get("weight").toString()) : 0.0;

        String email = (String) payload.get("userEmail");
        String address = (String) payload.get("shippingAddress");
        String phone = (String) payload.get("phoneNumber");

        return ResponseEntity.ok(singleOrderService.createPreorder(name, price, weight, email, address, phone));
    }

    @PatchMapping("/checkout")
    public ResponseEntity<String> finalizeUserOrders(@RequestBody Map<String, String> payload) {
        String email = payload.get("userEmail");
        String address = payload.get("shippingAddress");
        String phone = payload.get("phoneNumber");

        if (email == null || address == null) {
            return ResponseEntity.badRequest().body("Brak maila lub adresu!");
        }

        try {
            singleOrderService.updateContactDetailsForOpenOrders(email, address, phone);
            return ResponseEntity.ok("Zaktualizowano dane dostawy dla produktów w koszyku.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        try {
            singleOrderService.cancelPreorder(id);
            return ResponseEntity.ok("Zamówienie anulowane. Zaliczka zwrócona.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/close-cycle")
    public ResponseEntity<List<SingleOrder>> closeCycle() {
        List<SingleOrder> lockedOrders = singleOrderService.closeCycleAndCalculateCosts();
        return ResponseEntity.ok(lockedOrders);
    }

    @GetMapping("/by-email")
    public ResponseEntity<List<SingleOrder>> getOrdersByEmail(@RequestParam String mail) {
        List<SingleOrder> orders = singleOrderService.getOrdersByUserEmail(mail);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<SingleOrder>> createOrdersBulk(@RequestBody CreateOrderRequest request) {
        if (request.getUserEmail() == null || request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<SingleOrder> createdOrders = singleOrderService.createBulkOrders(request);
        return ResponseEntity.ok(createdOrders);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<SingleOrder> payOrder(@PathVariable Long id) {
        try {
            SingleOrder updatedOrder = singleOrderService.markOrderAsPaid(id);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}