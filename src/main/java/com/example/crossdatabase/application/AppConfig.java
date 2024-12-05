package com.example.crossdatabase.application;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SQLServerDriver msSqlDriver() {
        return new SQLServerDriver();
    }

    @Bean
    public Driver postgresDriver() {
        return new Driver();
    }
}
