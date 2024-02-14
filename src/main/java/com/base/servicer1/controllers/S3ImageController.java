package com.base.servicer1.controllers;

import com.base.servicer1.services.interfaces.IS3ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/r1/api/s3")
public class S3ImageController {

    private static final Logger logger = LogManager.getLogger(S3ImageController.class);

    @Autowired
    IS3ImageService s3ImageService;

    @GetMapping("/image/list")
    public ResponseEntity<List<String>> imageList() {
        logger.info("r1: s3 list images request received.");
        List<String> images = s3ImageService.listImages();
        logger.info("r1: s3 list images request processed.");

        return ResponseEntity.ok(images);
    }

    @GetMapping(value = "/image/{key}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String key) {
        logger.info("r1: s3 get image request received.");

        InputStreamResource resource = s3ImageService.getImage(key);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + key);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        logger.info("r1: s3 get image request processed.");

        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PutMapping("/image")
    public ResponseEntity<String> addImage(@RequestParam("image") MultipartFile file) throws IOException {
        logger.info("r1: s3 add image request received.");
        String versionId = s3ImageService.addImage(file);
        logger.info("r1: s3 add image request processed.");

        return ResponseEntity.ok(versionId);
    }

    @DeleteMapping("/image/{key}")
    public ResponseEntity<Void> deleteImage(@PathVariable String key) {
        logger.info("r1: s3 delete image request received.");
        s3ImageService.deleteImage(key);
        logger.info("r1: s3 delete image request processed.");

        return ResponseEntity.ok().build();
    }
}
