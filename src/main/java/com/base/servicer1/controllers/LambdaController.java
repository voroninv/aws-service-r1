package com.base.servicer1.controllers;

import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.base.servicer1.constants.Constants;
import com.base.servicer1.domain.Order;
import com.base.servicer1.exceptions.BadRequestException;
import com.base.servicer1.services.ILambdaService;
import com.base.servicer1.services.LambdaData;
import com.base.servicer1.services.LambdaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1/api/lambda")
public class LambdaController {

    private static final Logger logger = LogManager.getLogger(LambdaController.class);

    @Autowired
    ILambdaService lambdaService;

    @GetMapping
    @RequestMapping("/list")
    public ResponseEntity<List<FunctionConfiguration>> list() {
        logger.info("r1: lambda list request started.");
        List<FunctionConfiguration> functionConfigurations = lambdaService.listFunctions();
        logger.info("r1: lambda list request completed.");

        return ResponseEntity.ok(functionConfigurations);
    }

    @PostMapping("/invoke")
    public ResponseEntity<List<Order>> invoke(@RequestBody LambdaData lambdaData) {
        logger.info("r1: lambda started.");
        if (StringUtils.isBlank(lambdaData.getLambdaName())) {
            throw new BadRequestException(Constants.INCORRECT_REQUEST_PARAMETERS);
        }
        List<Order> orderList = lambdaService.invokeFunction(lambdaData);
        logger.info("r1: lambda completed.");

        return ResponseEntity.ok(orderList);
    }

}
