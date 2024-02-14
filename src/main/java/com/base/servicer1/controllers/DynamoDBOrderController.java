package com.base.servicer1.controllers;

import com.base.servicer1.entities.Order;
import com.base.servicer1.services.interfaces.IDynamoDbOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1/api/order")
public class DynamoDBOrderController {

    private static final Logger logger = LogManager.getLogger(DynamoDBOrderController.class);

    @Autowired
    IDynamoDbOrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<List<Order>> listOrders() {
        logger.info("r1: order list request received.");
        List<Order> orderList = orderService.listOrders();
        logger.info("r1: order list request processed.");

        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        logger.info("r1: order get request received.");
        Order order = orderService.getOrder(id);
        logger.info("r1: order get request processed.");

        return ResponseEntity.ok(order);
    }

    @PutMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        logger.info("r1: order add request received.");
        Order result = orderService.addOrder(order);
        logger.info("r1: order add request processed.");

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        logger.info("r1: order delete request received.");
        orderService.deleteOrder(id);
        logger.info("r1: order delete request processed.");

        return ResponseEntity.ok().build();
    }
}
