package com.base.servicer1.services.interfaces;

import com.base.servicer1.entities.Order;

import java.util.List;

public interface IDynamoDbOrderService {
    List<Order> listOrders();

    Order addOrder(Order order);

    Order getOrder(Integer id);
}
