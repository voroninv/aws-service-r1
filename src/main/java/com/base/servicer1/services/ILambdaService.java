package com.base.servicer1.services;

import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.base.servicer1.domain.Order;

import java.util.List;

public interface ILambdaService {
    List<Order> invokeFunction(LambdaData lambdaData);

    List<FunctionConfiguration> listFunctions();
}
