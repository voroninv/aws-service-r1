package com.base.servicer1.services;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.*;
import com.base.servicer1.entities.Order;
import com.base.servicer1.exceptions.JsonException;
import com.base.servicer1.services.interfaces.ILambdaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LambdaService implements ILambdaService {

    private static final String LAMBDA_LANGUAGE = "java17";
    private static final String LAMBDA_HANDLER = "org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest";
    private static final String LAMBDA_DESCRIPTION = "Lambda created with AWS SDK.";
    private static final String LAMBDA_DEFAULT_ROLE = "arn:aws:iam::637423326346:role/lambdaRole";

    @Autowired
    AWSLambda awsLambda;

    public List<FunctionConfiguration> listFunctions() {
        return awsLambda.listFunctions().getFunctions();
    }

    public List<Order> invokeFunction(String functionName) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(functionName);

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

    public String createFunction(String functionName, String role, MultipartFile file) throws IOException {
        FunctionCode code = new FunctionCode()
                .withZipFile(ByteBuffer.wrap(file.getBytes()));

        CreateFunctionRequest createRequest = new CreateFunctionRequest()
                .withFunctionName(functionName)
                .withDescription(LAMBDA_DESCRIPTION)
                .withRuntime(LAMBDA_LANGUAGE)
                .withRole(StringUtils.isBlank(role) ? LAMBDA_DEFAULT_ROLE : role)
                .withHandler(LAMBDA_HANDLER)
                .withCode(code);

        CreateFunctionResult createResult = awsLambda.createFunction(createRequest);

        return createResult.getFunctionArn();
    }

    public void deleteFunction(String functionName) {
        DeleteFunctionRequest deleteFunctionRequest = new DeleteFunctionRequest();
        deleteFunctionRequest.setFunctionName(functionName);

        awsLambda.deleteFunction(deleteFunctionRequest);
    }
}
