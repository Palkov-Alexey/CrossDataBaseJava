-- liquibase formatted sql
-- changeset alexey.palkov:create_all_tables
-- preconditions onFail:MARK_RAN onError:HALT

create table PRINTING_PROFILE
(
    Id      BIGINT auto_increment,

    name    varchar(255) not null,
    type    varchar(255) not null,
    profile text         not null,

    constraint PRINTING_PROFILE_PK
        primary key (Id)
);

create table SYS_PROP
(
    Id          BIGINT auto_increment,

    sys_key     varchar(255) not null,
    type        varchar(255) not null,
    value       text         not null,
    description varchar(255),

    constraint SYS_PROP_PK
        primary key (Id)
);

create table db_setting
(
    Id                     bigint auto_increment,
    Name                   varchar(100) not null,
    db_type                 varchar(100) not null,
    host                   varchar(100) not null,
    port                   int,
    instance               varchar(100),
    authentication_type varchar(100) not null,
    login                  varchar(255),
    password               text,
    url                    text,

    constraint db_setting_pk
        primary key (Id)
);



create unique index printing_profile_name_uindex
    on printing_profile (name);

create unique index sys_prop_sys_key_uindex
    on sys_prop (sys_key);
