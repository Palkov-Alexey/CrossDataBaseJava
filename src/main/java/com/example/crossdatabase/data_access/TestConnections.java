package com.example.crossdatabase.data_access;

import org.springframework.stereotype.Repository;

import com.example.crossdatabase.data_access.infrastructure.Driver;
import com.example.crossdatabase.enums.StatusType;
import com.example.crossdatabase.models.DbSettingModel;

@Repository
public class TestConnections {
    public StatusType test(DbSettingModel setting) {
        var template = Driver.getTemplate(setting, null);
        try {
            template.queryForList("select 1");
            return StatusType.Success;
        } catch (Exception e) {
            return StatusType.Error;
        }
    }
}
