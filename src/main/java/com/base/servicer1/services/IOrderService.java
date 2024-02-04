package com.base.servicer1.services;

import com.base.servicer1.domain.Order;

import java.util.List;

public interface IOrderService {
    List<Order> listOrders();

    Integer addOrder(Order order);

    Order getOrder(Integer id);
}
