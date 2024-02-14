package com.base.servicer1.services;

import com.base.servicer1.entities.Order;
import com.base.servicer1.repositories.OrderRepository;
import com.base.servicer1.services.interfaces.IDynamoDbOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamoDbOrderService implements IDynamoDbOrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> listOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);

        return orders;
    }

    public Order getOrder(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
