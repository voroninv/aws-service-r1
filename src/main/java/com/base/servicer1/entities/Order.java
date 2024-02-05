package com.base.servicer1.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "orders")
public class Order {
    @DynamoDBHashKey
    private int id;
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private double price;
    @DynamoDBAttribute
    private int quantity;
}
