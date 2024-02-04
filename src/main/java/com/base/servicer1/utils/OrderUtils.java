package com.base.servicer1.utils;


import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.base.servicer1.domain.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderUtils {

    public static Map<String, AttributeValue> toMap(Order order) {
        HashMap<String, AttributeValue> itemValues = new HashMap<>();

        AttributeValue id = new AttributeValue();
        id.setN(String.valueOf(order.getId()));
        AttributeValue price = new AttributeValue();
        price.setN(String.valueOf(order.getPrice()));
        AttributeValue quantity = new AttributeValue();
        quantity.setN(String.valueOf(order.getQuantity()));

        itemValues.put("id", id);
        itemValues.put("name", new AttributeValue(order.getName()));
        itemValues.put("price", price);
        itemValues.put("quantity", quantity);

        return itemValues;
    }

    public static Order toOrder(Map<String, AttributeValue> valueMap) {
        Order order = new Order();
        order.setId(Integer.parseInt(valueMap.get("id").getN()));
        order.setName(valueMap.get("name").getS());
        order.setPrice(Double.parseDouble(valueMap.get("price").getN()));
        order.setQuantity(Integer.parseInt(valueMap.get("quantity").getN()));

        return order;
    }
}
