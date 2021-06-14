package com.example.case6.controller;

import com.example.case6.model.Images;
import com.example.case6.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private IImageService imageService;

    @GetMapping
    public ResponseEntity<Iterable<Images>> getAllImage() {
        return new ResponseEntity<>(imageService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Images> createNewImage(@RequestBody Images image) {
        return new ResponseEntity<>(imageService.save(image), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Images> getImage(@PathVariable Long id) {
        Optional<Images> imageOptional = imageService.findById(id);
        return imageOptional.map(image -> new ResponseEntity<>(image, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Images> updateImage(@PathVariable Long id, @RequestBody Images image) {
        Optional<Images> imageOptional = imageService.findById(id);
        return imageOptional.map(image1 -> {
            image.setId(image1.getId());
            return new ResponseEntity<>(imageService.save(image), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Images> deleteImage(@PathVariable Long id) {
        Optional<Images> imageOptional = imageService.findById(id);
        return imageOptional.map(image -> {
            imageService.remove(id);
            return new ResponseEntity<>(image, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
