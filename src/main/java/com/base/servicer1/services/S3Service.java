package com.base.servicer1.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.base.servicer1.services.interfaces.IS3Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service implements IS3Service {

    private static final Logger logger = LogManager.getLogger(S3Service.class);

    @Autowired
    AmazonS3 amazonS3;

    public List<String> listBuckets() {
        return amazonS3.listBuckets().stream()
                .map(Bucket::getName)
                .collect(Collectors.toList());
    }

    public String createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            Bucket bucket = amazonS3.createBucket(new CreateBucketRequest(bucketName));
            logger.info(String.format("r1: s3 bucket: %s created.", bucketName));

            return bucket.getName();
        } else {
            return null;
        }
    }

    public void deleteBucket(String bucketName) {
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucketName);
        listObjectsV2Result.getObjectSummaries()
                .forEach(s3ObjectSummary -> {
                    amazonS3.deleteObject(bucketName, s3ObjectSummary.getKey());
                    logger.info(String.format("r1: s3 object: %s deleted.", s3ObjectSummary.getKey()));
                });
        amazonS3.deleteBucket(bucketName);
        logger.info(String.format("r1: s3 bucket: %s deleted.", bucketName));
    }
}
