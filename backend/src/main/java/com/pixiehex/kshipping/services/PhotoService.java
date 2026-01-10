package com.pixiehex.kshipping.services;

import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.InputStream;

@Service
public class PhotoService {

    private final String uploadDir = "uploaded_photos";

    public PhotoService() {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory", e);
            }
        }
    }

    public void savePhoto(MultipartFile file) throws IOException {
        Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
    }

    public byte[] getPhoto(String filename) throws IOException {
        String resourcePath = "photos/" + filename;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("File not found or not readable: " + filename);
            }
            return inputStream.readAllBytes();
        }
    }


    public String getContentType(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir, filename);
        return Files.probeContentType(filePath);
    }
}