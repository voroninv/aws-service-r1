package com.base.servicer1.services;

import com.base.servicer1.entities.Order;

import java.util.List;

public interface IOrderService {
    List<Order> listOrders();

    Order addOrder(Order order);

    Order getOrder(Integer id);
}
