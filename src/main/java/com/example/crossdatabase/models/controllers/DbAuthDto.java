package com.example.crossdatabase.models.controllers;

import com.example.crossdatabase.enums.DbAuthenticationType;
import com.example.crossdatabase.helpers.AnnotationHelper;

import jdk.jfr.Description;

public class DbAuthDto {
    private DbAuthenticationType type;

    public DbAuthDto(DbAuthenticationType type) {
        this.type = type;
    }

    public DbAuthenticationType getType() {
        return type;
    }

    public void setType(DbAuthenticationType type) {
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
