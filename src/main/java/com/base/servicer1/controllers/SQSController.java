package com.base.servicer1.controllers;

import com.base.servicer1.entities.SQSMessage;
import com.base.servicer1.services.interfaces.ISQSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1/api/sqs")
public class SQSController {

    private static final Logger logger = LogManager.getLogger(SQSController.class);

    @Autowired
    ISQSService sqsService;

    @GetMapping("/queue/list")
    public ResponseEntity<List<String>> listQueues() {
        logger.info("r1: sqs list queues request received.");
        List<String> queues = sqsService.listQueues();
        logger.info("r1: sqs list queues request processed.");

        return ResponseEntity.ok(queues);
    }

    @PutMapping("/queue/{queueName}")
    public ResponseEntity<String> createQueue(@PathVariable String queueName) {
        logger.info("r1: sqs create queue request received.");
        String queue = sqsService.createQueue(queueName);
        logger.info("r1: sqs create queue request processed.");

        return ResponseEntity.ok(queue);
    }

    @DeleteMapping("/queue/{queueName}")
    public ResponseEntity<String> deleteQueue(@PathVariable String queueName) {
        logger.info("r1: sqs delete queue request received.");
        sqsService.deleteQueue(queueName);
        logger.info("r1: sqs delete queue request processed.");

        return ResponseEntity.ok(queueName);
    }

    @PostMapping("/message/{queueName}")
    public ResponseEntity<String> sendMessage(@PathVariable String queueName, @RequestBody String message) {
        logger.info("r1: sqs send message request received.");
        String queue = sqsService.sendMessage(queueName, message);
        logger.info("r1: sqs send message request processed.");

        return ResponseEntity.ok(queue);
    }

    @GetMapping("/message/{queueName}")
    public ResponseEntity<List<SQSMessage>> receiveMessage(@PathVariable String queueName) {
        logger.info("r1: sqs receive message request received.");
        List<SQSMessage> messages = sqsService.receiveMessages(queueName);
        logger.info("r1: sqs receive message request processed.");

        return ResponseEntity.ok(messages);
    }
}
