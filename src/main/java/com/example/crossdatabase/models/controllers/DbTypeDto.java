package com.example.crossdatabase.models.controllers;

import com.example.crossdatabase.enums.DbType;
import com.example.crossdatabase.helpers.AnnotationHelper;

import jdk.jfr.Description;

public class DbTypeDto {
    private DbType type;

    public DbTypeDto(DbType type) {
        this.type = type;
    }

    public DbType getType() {
        return type;
    }

    public void setType(DbType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        try {
            var annotation = AnnotationHelper.getAnnotation(type, Description.class);
            return annotation.get().value();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
