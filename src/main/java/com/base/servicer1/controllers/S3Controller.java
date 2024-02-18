package com.base.servicer1.controllers;

import com.base.servicer1.services.interfaces.IS3Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1/api/s3")
public class S3Controller {

    private static final Logger logger = LogManager.getLogger(S3Controller.class);

    @Autowired
    IS3Service s3Service;

    @GetMapping("/bucket/list")
    public ResponseEntity<List<String>> listBuckets() {
        logger.info("r1: s3 list buckets request received.");
        List<String> buckets = s3Service.listBuckets();
        logger.info("r1: s3 list buckets request processed.");

        return ResponseEntity.ok(buckets);
    }

    @PutMapping("/bucket/create/{bucketName}")
    public ResponseEntity<String> createBucket(@PathVariable String bucketName) {
        logger.info("r1: s3 create bucket request received.");
        String bucket = s3Service.createBucket(bucketName);
        logger.info("r1: s3 create bucket request processed.");

        return ResponseEntity.ok(bucket);
    }

    @DeleteMapping("/bucket/delete/{bucketName}")
    public ResponseEntity<String> deleteBucket(@PathVariable String bucketName) {
        logger.info("r1: s3 delete bucket request received.");
        s3Service.deleteBucket(bucketName);
        logger.info("r1: s3 delete bucket request processed.");

        return ResponseEntity.ok("Ok");
    }
}
