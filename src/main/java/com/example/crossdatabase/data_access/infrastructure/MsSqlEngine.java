package com.example.crossdatabase.data_access.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.crossdatabase.enums.DbType;
import com.example.crossdatabase.interfaces.ISqlEngine;
import com.example.crossdatabase.models.DbSettingModel;
import com.example.crossdatabase.models.dao.DataBaseModel;

public class MsSqlEngine implements ISqlEngine {
    private final JdbcTemplate template;
    private final DbSettingModel setting;
    static final Logger logger = LogManager.getLogger(MsSqlEngine.class);

    public MsSqlEngine(DbSettingModel setting) {
        this.setting = setting;
        template = Driver.getTemplate(setting, null);
    }

    @Override
    public String toString() {
        return setting.getName();
    }

    public String getName() {
        return setting.getName();
    }

    public DbType getType() {
        return DbType.MsSql;
    }

    private List<String> getDataBases() throws Exception {
        try {
            List<String> result = new ArrayList<String>();
            List<String> resultQuery = template.queryForList("select name from sys.databases;", String.class);

            for (String res : resultQuery) {
                result.add(res);
            }

            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e);
        }
    }

    public List<DataBaseModel> getSchema() throws Exception {
        List<DataBaseModel> result = new ArrayList<DataBaseModel>();
        for (var database : getDataBases()) {
            var sql = String.format(
                    "select TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME from %s.INFORMATION_SCHEMA.TABLES  order by TABLE_SCHEMA, TABLE_NAME;",
                    database);
            try {
                var resultQuery = template.query(sql, new BeanPropertyRowMapper<DataBaseModel>(DataBaseModel.class));
                logger.info("DataBase: " + database + ", count tables: " + result.size());

                result.addAll(resultQuery);
            } catch (UncategorizedSQLException e) {
                var error = e.getSQLException();
                if (error.getErrorCode() != 916) {
                    logger.error(error.getMessage());
                    throw new Exception(e);
                }

                logger.warn(error.getMessage());
            }
        }

        return result;
    }
}
