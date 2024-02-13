package com.base.servicer1.services.interfaces;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IS3ImageService {
    String addImage(MultipartFile multipartFile) throws IOException;

    InputStreamResource getImage(String name);

    List<String> listImages();
}
