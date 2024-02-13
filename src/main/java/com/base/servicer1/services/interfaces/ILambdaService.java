package com.base.servicer1.services.interfaces;

import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.base.servicer1.entities.Order;
import com.base.servicer1.models.LambdaData;

import java.util.List;

public interface ILambdaService {
    List<Order> invokeFunction(LambdaData lambdaData);

    List<FunctionConfiguration> listFunctions();
}
