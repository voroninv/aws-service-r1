package com.base.servicer1.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamoDbService implements IDynamoDbService {

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    public List<String> listTables() {
        return amazonDynamoDB.listTables().getTableNames();
    }

    public String createTable(String tableName) {
        CreateTableRequest request = new CreateTableRequest()
                .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.N))
                .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                .withProvisionedThroughput(new ProvisionedThroughput(
                        new Long(10), new Long(10)))
                .withTableName(tableName);

        CreateTableResult result = amazonDynamoDB.createTable(request);
        return result.getTableDescription().getTableStatus();
    }

    public String describeTable(String tableName) {
        TableDescription table = amazonDynamoDB.describeTable(tableName).getTable();

        return String.format("Name: %1$s, id: %2$s, arn: %3$s, status: %4$s, count: %5$d",
                table.getTableName(),
                table.getTableId(),
                table.getTableArn(),
                table.getTableStatus(),
                table.getItemCount());
    }

    public String deleteTable(String tablaName) {
        return amazonDynamoDB.deleteTable(tablaName).getTableDescription().getTableStatus();
    }
}
