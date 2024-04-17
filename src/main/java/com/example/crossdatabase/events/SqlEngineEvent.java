package com.example.crossdatabase.events;

import org.springframework.context.ApplicationEvent;

import com.example.crossdatabase.interfaces.ISqlEngine;

public class SqlEngineEvent extends ApplicationEvent {
    public final ISqlEngine stage;

    public SqlEngineEvent(ISqlEngine stage){
        super(stage);
        this.stage = stage;
    }
}
