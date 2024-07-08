package com.example.demoauth.service;

import com.example.demoauth.DTO.ImageDTO;
import com.example.demoauth.models.Category;
import com.example.demoauth.models.Image;
import com.example.demoauth.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void save(Image image) {
        imageRepository.save(image);
    }

    public void deleteImage(Long imageId) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        if(optionalImage.isPresent()) {
            Image image = optionalImage.get();
            imageRepository.delete(image);
        } else {
            throw new RuntimeException("Image not found with id: " + imageId);
        }
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream()
                .map(this::mapImageDetails)
                .collect(Collectors.toList());
    }

    private ImageDTO mapImageDetails(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setImgUrl(image.getImgUrl());

        return imageDTO;
    }

    public Image updateImage(Long imageId, Image updatedImage) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            Image existingImage = optionalImage.get();

            existingImage.setImgUrl(updatedImage.getImgUrl());
            imageRepository.save(existingImage);

            return existingImage;
        } else {
            throw new RuntimeException("Image not found with id: " + imageId + "for updating.");
        }
    }
}
