package com.base.servicer1.services.interfaces;

import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.base.servicer1.entities.Order;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ILambdaService {
    List<FunctionConfiguration> listFunctions();

    List<Order> invokeFunction(String functionName);

    String createFunction(String functionName, String role, MultipartFile file) throws IOException;

    void deleteFunction(String functionName);
}
