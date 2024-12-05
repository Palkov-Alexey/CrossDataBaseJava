package com.example.crossdatabase.models.dao;

import com.example.crossdatabase.enums.DbAuthenticationType;
import com.example.crossdatabase.enums.DbType;
import jakarta.persistence.*;

@Entity
@Table(name = "db_setting")
public class DbSettingDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "db_type")
    @Enumerated(EnumType.STRING)
    private DbType dbType;

    private String host;

    private String instance;

    private String port;

    @Column(name = "authentication_type")
    @Enumerated(EnumType.STRING)
    private DbAuthenticationType authenticationType;

    private String login;

    @Lob
    private String password;

    @Lob
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
