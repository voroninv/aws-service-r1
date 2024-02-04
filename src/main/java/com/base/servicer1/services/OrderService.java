package com.base.servicer1.services;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.base.servicer1.domain.Order;
import com.base.servicer1.utils.OrderUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private static final String TABLE_NAME = "orders";

    private final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new ProfileCredentialsProvider())
            .withRegion(Regions.EU_NORTH_1)
            .build();

    public List<Order> listOrders() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(TABLE_NAME);

        ScanResult scanResult = ddb.scan(scanRequest);

        return scanResult.getItems().stream()
                .map(OrderUtils::toOrder)
                .collect(Collectors.toList());
    }

    public Order getOrder(Integer id) {
        HashMap<String, AttributeValue> key = new HashMap<>();
        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setN(String.valueOf(id));
        key.put("id", attributeValue);

        GetItemResult item = ddb.getItem(new GetItemRequest(TABLE_NAME, key));

        return OrderUtils.toOrder(item.getItem());
    }

    public Integer addOrder(Order order) {
        PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName(TABLE_NAME)
                .withItem(OrderUtils.toMap(order));

        PutItemResult putItemResult = ddb.putItem(putItemRequest);

        return putItemResult.getSdkHttpMetadata().getHttpStatusCode();
    }
}
