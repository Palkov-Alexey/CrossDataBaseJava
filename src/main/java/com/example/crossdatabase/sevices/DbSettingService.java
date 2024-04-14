package com.example.crossdatabase.sevices;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.*;
import org.springframework.stereotype.Service;

import com.example.crossdatabase.data_access.infrastructure.MsSqlEngine;
import com.example.crossdatabase.data_access.infrastructure.PostgresEngine;
import com.example.crossdatabase.interfaces.ISqlEngine;
import com.example.crossdatabase.models.DbSettingModel;
import com.example.crossdatabase.models.converters.DbSettingConverter;
import com.example.crossdatabase.models.dao.DbSettingDto;
import com.example.crossdatabase.repository.DbSettingRepo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jakarta.annotation.PostConstruct;

@Service
public class DbSettingService {
    static final Logger logger = LogManager.getLogger(PropService.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private final DbSettingRepo dbSettingRepo;
    private final DbSettingConverter converter;

    public DbSettingService(DbSettingRepo dbSettingRepo, DbSettingConverter converter) {
        this.dbSettingRepo = dbSettingRepo;
        this.converter = converter;
    }

    @PostConstruct
    private void PropServiceInitialize() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule simpleKeyModule = new SimpleModule();
        // simpleKeyModule.addKeyDeserializer(IProp.class, new IPropKeyDeserializer());
        mapper.registerModule(simpleKeyModule);
    }

    public void saveSetting(DbSettingModel setting) {
        Optional<DbSettingDto> dto = dbSettingRepo.findById(setting.getId());

        if (dto.isPresent()) {
            var dtoId = dto.get().getId();
            dbSettingRepo.save(converter.toEntity(setting, dtoId));
            return;
        }

        dbSettingRepo.save(converter.toEntity(setting));
    }

    public Set<DbSettingModel> getSettingById(Long id) {
        return dbSettingRepo
                .findById(id)
                .stream()
                .map(converter::toModel)
                .collect(Collectors.toSet());
    }

    public Set<ISqlEngine> getEngines() {
        var settings = getSettings();
        var engines = new HashSet<ISqlEngine>();

        for (var setting : settings) {
            switch (setting.getDbType()) {
                case MsSql:
                    engines.add(new MsSqlEngine(setting));
                    break;
                case Postgres:
                    engines.add(new PostgresEngine(setting));
                    break;
            }
        }

        return engines;
    }

    public Set<DbSettingModel> getSettings() {
        return dbSettingRepo
                .findAll()
                .stream()
                .map(converter::toModel)
                .collect(Collectors.toSet());
    }
}