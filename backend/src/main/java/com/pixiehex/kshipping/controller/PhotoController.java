package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Product;
import com.pixiehex.kshipping.services.PhotoService;
import com.pixiehex.kshipping.services.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final ProductService productService;

    public PhotoController(PhotoService photoService, ProductService productService) {
        this.photoService = photoService;
        this.productService = productService;
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

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        try {
            Optional<Product> optionalProduct = productService.getProductById(id);
            if (optionalProduct.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Product product = optionalProduct.get();

            String filename;
            switch (product.getType()) {
                case "Serum":
                    filename = "serum.jpg";
                    break;
                case "Cream":
                    filename = "cream.png";
                    break;
                case "Toner":
                    filename = "toner.png";
                    break;
                default:
                    filename = "essence.png";
                    break;
            }

            byte[] photoBytes = photoService.getPhoto(filename);
            String contentType = photoService.getContentType(filename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(photoBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
