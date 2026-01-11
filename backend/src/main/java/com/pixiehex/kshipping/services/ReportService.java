package com.pixiehex.kshipping.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ReportService {

    private final String REPORT_DIR = "./src/main/resources/reports/";

    public ReportService() {
        Path path = Paths.get(REPORT_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Could not create reports directory", e);
            }
        }
    }
    public byte[] getReportPdf(String filename) throws IOException {
        Path filePath = Paths.get(REPORT_DIR, filename);

        if (!Files.exists(filePath)) {
            throw new IOException("Raport nie istnieje: " + filename);
        }
        return Files.readAllBytes(filePath);
    }
}