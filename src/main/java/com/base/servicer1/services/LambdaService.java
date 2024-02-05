package com.base.servicer1.services;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.*;
import com.base.servicer1.entities.Order;
import com.base.servicer1.exceptions.JsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LambdaService implements ILambdaService {

    @Autowired
    AWSLambda awsLambda;

    public List<FunctionConfiguration> listFunctions() {

        return awsLambda.listFunctions().getFunctions();
    }

    public List<Order> invokeFunction(LambdaData lambdaData) {

        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(lambdaData.getLambdaName())
                .withPayload(lambdaData.getLambdaPayload());

        InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
        String result = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(result, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage());
        }
    }
}
