package com.example.crossdatabase.sevices;

import org.apache.logging.log4j.*;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jakarta.annotation.PostConstruct;

@Service
public class PropService {
    static final Logger logger = LogManager.getLogger(PropService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

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

}
