package com.base.servicer1.controllers;

import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.base.servicer1.constants.Constants;
import com.base.servicer1.entities.Order;
import com.base.servicer1.exceptions.BadRequestException;
import com.base.servicer1.services.interfaces.ILambdaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/r1/api/lambda")
public class LambdaController {

    private static final Logger logger = LogManager.getLogger(LambdaController.class);

    @Autowired
    ILambdaService lambdaService;

    @GetMapping("/list")
    public ResponseEntity<List<FunctionConfiguration>> listFunctions() {
        logger.info("r1: lambda list request received.");
        List<FunctionConfiguration> functionConfigurations = lambdaService.listFunctions();
        logger.info("r1: lambda list request processed.");

        return ResponseEntity.ok(functionConfigurations);
    }

    @PostMapping("/invoke")
    public ResponseEntity<List<Order>> invokeFunction(@RequestParam String functionName) {
        logger.info("r1: lambda started.");
        if (StringUtils.isBlank(functionName)) {
            throw new BadRequestException(Constants.INCORRECT_REQUEST_PARAMETERS);
        }
        List<Order> orderList = lambdaService.invokeFunction(functionName);
        logger.info("r1: lambda completed.");

        return ResponseEntity.ok(orderList);
    }


    @PutMapping
    public ResponseEntity<String> createFunction(@RequestParam String functionName,
                                                 @RequestParam(required = false) String role,
                                                 @RequestParam("jar") MultipartFile file) throws IOException {
        logger.info("r1: lambda create request received.");
        String functionArn = lambdaService.createFunction(functionName, role, file);
        logger.info("r1: lambda create request processed.");

        return ResponseEntity.ok(functionArn);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteFunction(@RequestParam String functionName) {
        logger.info("r1: lambda delete request received.");
        lambdaService.deleteFunction(functionName);
        logger.info("r1: lambda delete request processed.");

        return ResponseEntity.ok().build();
    }
}
