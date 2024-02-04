package com.base.servicer1.controllers;

import com.base.servicer1.domain.Order;
import com.base.servicer1.services.IOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1/api/order")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    IOrderService orderService;

    @GetMapping
    @RequestMapping("/list")
    public ResponseEntity<List<Order>> listOrders() {
        logger.info("r1: order list request received.");
        List<Order> orderList = orderService.listOrders();
        logger.info("r1: order list request processed.");

        return ResponseEntity.ok(orderList);
    }

    @GetMapping
    @RequestMapping("/get/{id}")
    public ResponseEntity<Order> listOrders(@PathVariable Integer id) {
        logger.info("r1: order get request received.");
        Order order = orderService.getOrder(id);
        logger.info("r1: order get request processed.");

        return ResponseEntity.ok(order);
    }

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<Integer> addOrder(@RequestBody Order order) {
        logger.info("r1: order add request received.");
        Integer status = orderService.addOrder(order);
        logger.info("r1: order add request processed.");

        return ResponseEntity.ok(status);
    }
}
