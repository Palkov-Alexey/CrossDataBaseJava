package com.example.crossdatabase.models.dao;

public class DataBaseModel {
    private String table_catalog;

    private String table_schema;

    private String table_name;

    public String getTableCatalog() {
        return table_catalog;
    }

    public void setTableCatalog(String dataBaseName) {
        this.table_catalog = dataBaseName;
    }

    public String getTableSchema() {
        return table_schema;
    }

    public void setTableSchema(String tableSchema) {
        this.table_schema = tableSchema;
    }

    public String getTableName() {
        return table_name;
    }

    public void setTableName(String tableName) {
        this.table_name = tableName;
    }
}
