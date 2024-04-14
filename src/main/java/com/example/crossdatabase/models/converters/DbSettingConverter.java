package com.example.crossdatabase.models.converters;

import org.springframework.stereotype.Component;

import com.example.crossdatabase.models.DbSettingModel;
import com.example.crossdatabase.models.dao.DbSettingDto;

@Component
public class DbSettingConverter {
    public DbSettingModel toModel(DbSettingDto dto){
        return new DbSettingModel(dto);
    }

    public DbSettingDto toEntity(DbSettingModel model) {
        var entity = new DbSettingDto();
        entity.setName(model.getName());
        entity.setDbType(model.getDbType());
        entity.setHost(model.getHost());
        entity.setInstance(model.getInstance());
        entity.setPort(model.getPort());
        entity.setAuthType(model.getAuthType());
        entity.setLogin(model.getLogin());
        entity.setPassword(model.getPassword());
        entity.setUrl(model.getUrl());

        return entity;
    }

    public DbSettingDto toEntity(DbSettingModel model, Long id) {
        var entity = new DbSettingDto();
        entity.setId(id);
        entity.setName(model.getName());
        entity.setDbType(model.getDbType());
        entity.setHost(model.getHost());
        entity.setInstance(model.getInstance());
        entity.setPort(model.getPort());
        entity.setAuthType(model.getAuthType());
        entity.setLogin(model.getLogin());
        entity.setPassword(model.getPassword());
        entity.setUrl(model.getUrl());

        return entity;
    }
}