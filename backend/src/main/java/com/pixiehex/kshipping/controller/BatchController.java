package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.GroupOrder;
import com.pixiehex.kshipping.repository.GroupOrderRepository;
import com.pixiehex.kshipping.services.BatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
@CrossOrigin(origins = "") // Odblokowuje dostęp dla Reacta
public class BatchController {

    private final BatchService batchService;
    private final GroupOrderRepository groupOrderRepository;

    public BatchController(BatchService batchService, GroupOrderRepository groupOrderRepository) {
        this.batchService = batchService;
        this.groupOrderRepository = groupOrderRepository;
    }

    // --- 1. CZERWONY PRZYCISK (Uruchamia logikę pakowania) ---
    // Endpoint: POST http://localhost:8080/batch/run
    @PostMapping("/run")
    public ResponseEntity<String> runBatching() {
        // To wywołuje Twój algorytm + generowanie PDF
        String result = batchService.processBatching();
        return ResponseEntity.ok(result);
    }

    // --- 2. LISTA PACZEK (Dla panelu admina, żeby pobrać PDFy) ---
    // Endpoint: GET http://localhost:8080/batch/groups
    @GetMapping("/groups")
    public ResponseEntity<List<GroupOrder>> getAllGroups() {
        // Zwracamy listę wszystkich utworzonych paczek
        return ResponseEntity.ok(groupOrderRepository.findAll());
    }
}