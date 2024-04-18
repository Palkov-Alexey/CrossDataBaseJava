package com.example.crossdatabase.interfaces;

import java.util.List;

import com.example.crossdatabase.enums.DbType;
import com.example.crossdatabase.models.dao.DataBaseModel;

public interface ISqlEngine {
    DbType getType();

    String getName();

    List<DataBaseModel> getSchema() throws Exception;
}
