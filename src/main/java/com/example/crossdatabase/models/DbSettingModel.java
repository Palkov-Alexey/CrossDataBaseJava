package com.example.crossdatabase.models;

import com.example.crossdatabase.enums.DbAuthenticationType;
import com.example.crossdatabase.enums.DbType;
import com.example.crossdatabase.models.dao.DbSettingDto;

public class DbSettingModel {
    public DbSettingModel() {
    }

    public DbSettingModel(DbSettingDto dto) {
        id = dto.getId();
        name = dto.getName();
        dbType = dto.getDbType();
        host = dto.getHost();
        instance = dto.getInstance();
        port = dto.getPort();
        authenticationType = dto.getAuthType();
        login = dto.getLogin();
        password = dto.getPassword();
        url = dto.getUrl();
    }

    private Long id = 0l;

    private String name;

    private DbType dbType;

    private String host;

    private String instance;

    private String port;

    private DbAuthenticationType authenticationType;

    private String login;

    private String password;

    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType value) {
        this.dbType = value;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String value) {
        host = value;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String value) {
        instance = value;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String value) {
        port = value;
    }

    public DbAuthenticationType getAuthType() {
        return authenticationType;
    }

    public void setAuthType(DbAuthenticationType value) {
        authenticationType = value;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String value) {
        login = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        url = value;
    }
}
