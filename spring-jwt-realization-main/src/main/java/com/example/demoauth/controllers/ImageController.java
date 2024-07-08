package com.example.demoauth.controllers;

import com.example.demoauth.DTO.ImageDTO;
import com.example.demoauth.models.Image;
import com.example.demoauth.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/add")
    public ResponseEntity<Image> addImage(@RequestBody Image image) {
        Image newImage = new Image(image.getImgUrl());
        imageService.save(newImage);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        try {
            List<ImageDTO> imageDTOList = imageService.getAllImages();
            return ResponseEntity.ok(imageDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<Image> updateImage(@PathVariable Long imageId, @RequestBody Image image) {
        try {
            Image updateImage = imageService.updateImage(imageId, image);
            return ResponseEntity.ok(updateImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
