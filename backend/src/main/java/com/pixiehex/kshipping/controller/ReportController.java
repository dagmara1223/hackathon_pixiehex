package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.services.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            // 1. Pobieramy bajty z serwisu
            byte[] pdfBytes = reportService.getReportPdf(fileName);

            // 2. Zwracamy z nagłówkami PDF
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    // "inline" = otwórz w przeglądarce, "attachment" = pobierz
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(pdfBytes);

        } catch (IOException e) {
            // Jeśli plik nie istnieje -> 404
            return ResponseEntity.notFound().build();
        }
    }
}