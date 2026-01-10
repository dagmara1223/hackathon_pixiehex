package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.GroupOrder;
import com.pixiehex.kshipping.services.GroupOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group_order")
public class GroupOrderController {
    private final GroupOrderService groupOrderService = new GroupOrderService();
    @GetMapping
    public ResponseEntity<Object> getGroupOrder() {

        List<GroupOrder> orders = groupOrderService.findAllGroups();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGroupOrderById(@PathVariable long id) {
        GroupOrder order = groupOrderService.findGroupById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Object> addGroupOrder(@Valid @RequestBody GroupOrder GroupOrder) {
        GroupOrder order = groupOrderService.saveGroup(GroupOrder);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGroupOrder(@Valid @RequestBody GroupOrder GroupOrder, @PathVariable int id) {
        GroupOrder order = groupOrderService.updateGroup(GroupOrder);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroupOrder(@PathVariable long id) {
        groupOrderService.deleteGroupById(id);
        return ResponseEntity.ok("Deleted succesfully");
    }

}
