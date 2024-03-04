package com.base.servicer1;

import com.base.servicer1.controllers.S3Controller;
import com.base.servicer1.controllers.S3ImageController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceR1ApplicationTests {

    @Autowired
    S3Controller s3Controller;

    @Autowired
    S3ImageController s3ImageController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(s3ImageController);
        Assertions.assertNotNull(s3Controller);
    }
}
