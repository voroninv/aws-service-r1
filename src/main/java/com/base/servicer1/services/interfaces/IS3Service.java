package com.base.servicer1.services.interfaces;

import java.util.List;

public interface IS3Service {
    List<String> listBuckets();

    String createBucket(String bucketName);

    void deleteBucket(String bucketName);
}
