package com.example.crossdatabase.interfaces;

import java.util.List;

import com.example.crossdatabase.models.dao.DataBaseModel;

public interface ISqlEngine {
    String getName();

    List<DataBaseModel> getSchema() throws Exception;
}
