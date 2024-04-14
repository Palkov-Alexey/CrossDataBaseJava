package com.example.crossdatabase.data_access.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.crossdatabase.interfaces.ISqlEngine;
import com.example.crossdatabase.models.DbSettingModel;
import com.example.crossdatabase.models.dao.DataBaseModel;

public class PostgresEngine implements ISqlEngine {
    private JdbcTemplate template;
    private final DbSettingModel setting;
    static final Logger logger = LogManager.getLogger(PostgresEngine.class);

    public PostgresEngine(DbSettingModel setting) {
        this.setting = setting;
        template = Driver.getTemplate(setting, null);
    }

    public String getName() {
        return setting.getName();
    }

    private List<String> getDataBases() throws Exception {
        try {
            List<String> result = new ArrayList<String>();
            List<String> resultQuery = template.queryForList("select datname from pg_database where datistemplate = false and datname not like '%template%' order by lower(datname);", String.class);
            
            for (var res : resultQuery) {
                result.add(res);
            }

            template.getDataSource().getConnection().close();
            return result;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<DataBaseModel> getSchema() throws Exception {
        try {
            List<DataBaseModel> result = new ArrayList<DataBaseModel>();
            
            for (var database : getDataBases()) {
                logger.info(database);
                template = Driver.getTemplate(setting, database);
                var sql = "select table_catalog, table_schema, table_name from information_schema.tables where table_schema = 'public';";

                var resultQuery = template.query(sql, new BeanPropertyRowMapper<DataBaseModel>(DataBaseModel.class));

                result.addAll(resultQuery);
                template.getDataSource().getConnection().close();
            }

            template.getDataSource().getConnection().close();

            return result;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
