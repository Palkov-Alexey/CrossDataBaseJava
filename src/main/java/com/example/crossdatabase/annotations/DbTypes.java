package com.example.crossdatabase.annotations;

import com.example.crossdatabase.enums.DbType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DbTypes {
    DbType[] types();
}
