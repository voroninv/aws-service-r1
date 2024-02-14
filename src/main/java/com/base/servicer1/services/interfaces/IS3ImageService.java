package com.base.servicer1.services.interfaces;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IS3ImageService {
    List<String> listImages();

    InputStreamResource getImage(String key);

    String addImage(MultipartFile multipartFile) throws IOException;

    void deleteImage(String key);
}
