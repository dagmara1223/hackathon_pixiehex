package com.pixiehex.kshipping.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(filename).normalize();

        if (!filePath.startsWith(uploadPath)) {
            throw new SecurityException("Invalid file path: potential path traversal attempt");
        }

        if (!Files.exists(filePath)) {
            throw new IOException("File not found");
        }
        return Files.readAllBytes(filePath);
    }

    public String getContentType(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir, filename);
        return Files.probeContentType(filePath);
    }
}
