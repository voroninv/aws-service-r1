package com.base.servicer1.services.interfaces;

import java.util.List;

public interface IDynamoDbService {
    List<String> listTables();

    String createTable(String tableName);

    String describeTable(String tableName);

    String deleteTable(String tablaName);

}
