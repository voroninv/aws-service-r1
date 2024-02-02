package com.base.servicer1.services;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.*;
import com.base.servicer1.domain.Order;
import com.base.servicer1.exceptions.JsonException;
import com.base.servicer1.exceptions.LambdaNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LambdaService implements ILambdaService {

    public List<Order> invokeFunction(LambdaData lambdaData) {

        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(lambdaData.getLambdaName())
                .withPayload(lambdaData.getLambdaPayload());

        InvokeResult invokeResult;

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.EU_NORTH_1).build();

            invokeResult = awsLambda.invoke(invokeRequest);
            String result = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(result, new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                throw new JsonException(e.getMessage());
            }
        } catch (ResourceNotFoundException e) {
            throw new LambdaNotFoundException(e.getMessage());
        }
    }

    public List<FunctionConfiguration> listFunctions() {
        ListFunctionsResult functionResult;

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.EU_NORTH_1).build();

            functionResult = awsLambda.listFunctions();

            return functionResult.getFunctions();
        } catch (ResourceNotFoundException e) {
            throw new LambdaNotFoundException(e.getMessage());
        }
    }
}
