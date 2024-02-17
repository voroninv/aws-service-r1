package com.base.servicer1.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.base.servicer1.services.interfaces.IS3ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class S3ImageService implements IS3ImageService {

    private static final Logger logger = LogManager.getLogger(S3ImageService.class);
    private static final String IMAGE_BUCKET_NAME = "eu-north-1-images";
    private static final String SERVICE_NAME = "service-r1";

    @Autowired
    AmazonS3 amazonS3;

    @Override
    public List<String> listImages() {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request();
        listObjectsV2Request.setBucketName(IMAGE_BUCKET_NAME);
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(listObjectsV2Request);
        logger.info(String.format("r1: s3 image list retrieved for %s bucket.", IMAGE_BUCKET_NAME));

        return listObjectsV2Result.getObjectSummaries()
                .stream().map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public InputStreamResource getImage(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(
                IMAGE_BUCKET_NAME,
                key
        );
        S3Object object = amazonS3.getObject(getObjectRequest);
        logger.info(String.format("r1: s3 image: %s retrieved.", key));

        return new InputStreamResource(object.getObjectContent());
    }

    @Override
    public String addImage(MultipartFile multipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setUserMetadata(
                Map.of(
                        "contentType", Objects.requireNonNull(multipartFile.getContentType()),
                        "name", multipartFile.getName(),
                        "size", String.valueOf(multipartFile.getSize()),
                        "service", SERVICE_NAME
                )
        );

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                IMAGE_BUCKET_NAME,
                multipartFile.getOriginalFilename(),
                multipartFile.getInputStream(),
                objectMetadata
        );
        PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);
        String versionId = putObjectResult.getVersionId();
        logger.info(String.format("r1: s3 image: %s added.", versionId));

        return versionId;
    }

    @Override
    public void deleteImage(String key) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(IMAGE_BUCKET_NAME, key);
        amazonS3.deleteObject(deleteObjectRequest);
        logger.info(String.format("r1: s3 image: %s deleted.", key));
    }
}
