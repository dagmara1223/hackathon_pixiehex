package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.SingleOrder;
import com.pixiehex.kshipping.services.SingleOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/single_orders")
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

        return ResponseEntity.ok(singleOrderService.createPreorder(name, price, weight, email, address));
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
}