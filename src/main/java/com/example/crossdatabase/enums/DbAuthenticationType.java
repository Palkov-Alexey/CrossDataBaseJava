package com.example.crossdatabase.enums;

import com.example.crossdatabase.annotations.DbTypes;
import jdk.jfr.Description;

public enum DbAuthenticationType {
    @Description("Windows Credentials")
    @DbTypes(types = {DbType.MsSql})
    WindowsCredentials,

    @Description("User & Password")
    @DbTypes(types = {DbType.MsSql, DbType.Postgres})
    UserAndPassword
}
