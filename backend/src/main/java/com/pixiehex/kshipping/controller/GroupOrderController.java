package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.GroupOrder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group_order")
public class GroupOrderController {
    @GetMapping
    public ResponseEntity<Object> getGroupOrder() {
        return ResponseEntity.ok(new GroupOrder());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGroupOrderById() {
        return ResponseEntity.ok(new GroupOrder());
    }

    @PostMapping
    public ResponseEntity<Object> addGroupOrder(@Valid @RequestBody GroupOrder GroupOrder) {
        return ResponseEntity.ok(new GroupOrder());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGroupOrder(@Valid @RequestBody GroupOrder GroupOrder, @PathVariable int id) {
        return ResponseEntity.ok(new GroupOrder());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroupOrder(@PathVariable int id) {
        return ResponseEntity.ok(new GroupOrder());
    }

}