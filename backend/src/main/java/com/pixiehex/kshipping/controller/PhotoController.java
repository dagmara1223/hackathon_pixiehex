package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.services.PhotoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<String> addPhoto(@RequestPart("image") MultipartFile image) {
        try {
            photoService.savePhoto(image);
            return ResponseEntity.ok("Photo uploaded successfully: " + image.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo");
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String filename) {
        try {
            byte[] photoBytes = photoService.getPhoto(filename);
            String contentType = photoService.getContentType(filename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(photoBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
