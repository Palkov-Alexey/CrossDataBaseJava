package com.example.crossdatabase.data_access.infrastructure;

import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.crossdatabase.enums.DbAuthenticationType;
import com.example.crossdatabase.enums.DbType;
import com.example.crossdatabase.models.DbSettingModel;

public class Driver {
    public final static JdbcTemplate getTemplate(DbSettingModel setting, String catalog) {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getDriverName(setting.getDbType()));
        dataSource.setUrl(catalog == null ? setting.getUrl() : getUrl(setting, catalog));
        dataSource.setCatalog(getCatalog(setting.getDbType()));

        if (setting.getAuthType() == DbAuthenticationType.LoginAndPassword) {
            dataSource.setUsername(setting.getLogin());
            dataSource.setPassword(setting.getPassword());
        }

        return new JdbcTemplate(dataSource);
    }

    public final static String getDriverName(DbType dbType) {
        return switch (dbType) {
            case MsSql -> "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            case Postgres -> "org.postgresql.Driver";
        };
    }

    public final static String getCatalog(DbType dbType) {
        return switch (dbType) {
            case MsSql -> "master";
            case Postgres -> "outsource_Client";
        };
    }

    public final static String getUrl(DbSettingModel setting, String catalog) {
        return switch (setting.getDbType()) {
            case MsSql -> {
                var url = "jdbc:sqlserver://"
                        + setting.getHost()
                        + (Objects.equals(setting.getInstance(), "") ? "" : "/" + setting.getInstance())
                        + (Objects.equals(setting.getPort(), "") ? ";" : ":" + setting.getPort() + ";")
                        + "encrypt=false;";

                if (setting.getAuthType() == DbAuthenticationType.WindowsCredentials) {
                    url += "integratedSecurity=true;";
                }

                yield url;
            }
            case Postgres -> "jdbc:postgresql://"
                    + setting.getHost()
                    + (Objects.equals(setting.getPort(), "") ? "" : ":" + setting.getPort() + "")
                    + (catalog == null ? "/postgres" : "/" + catalog);
        };
    }
}
