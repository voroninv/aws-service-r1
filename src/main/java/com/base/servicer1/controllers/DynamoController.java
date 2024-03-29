package com.base.servicer1.controllers;

import com.base.servicer1.services.interfaces.IDynamoDbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1/api/dynamo")
public class DynamoController {

    private static final Logger logger = LogManager.getLogger(DynamoController.class);

    @Autowired
    IDynamoDbService dynamoDbService;

    @GetMapping("/table/list")
    public ResponseEntity<List<String>> listTables() {
        logger.info("r1: dynamoDb list tables request received.");
        List<String> tables = dynamoDbService.listTables();
        logger.info("r1: dynamoDb list tables request processed.");

        return ResponseEntity.ok(tables);
    }

    @PutMapping("/table/{tableName}")
    public ResponseEntity<String> createTable(@PathVariable String tableName, @RequestParam Boolean idIsNumber) {
        logger.info("r1: dynamoDb create table request received.");
        String table = dynamoDbService.createTable(tableName, idIsNumber);
        logger.info("r1: dynamoDb create table request processed.");

        return ResponseEntity.ok(table);
    }

    @GetMapping("/table/describe/{tableName}")
    public ResponseEntity<String> describeTable(@PathVariable String tableName) {
        logger.info("r1: dynamoDb describe table request received.");
        String table = dynamoDbService.describeTable(tableName);
        logger.info("r1: dynamoDb describe table request processed.");

        return ResponseEntity.ok(table);
    }

    @DeleteMapping("/table/{tableName}")
    public ResponseEntity<String> deleteTable(@PathVariable String tableName) {
        logger.info("r1: dynamoDb delete table request received.");
        String table = dynamoDbService.deleteTable(tableName);
        logger.info("r1: dynamoDb delete table request processed.");

        return ResponseEntity.ok(table);
    }
}
