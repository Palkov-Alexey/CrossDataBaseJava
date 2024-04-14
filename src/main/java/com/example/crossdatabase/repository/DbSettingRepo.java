package com.example.crossdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crossdatabase.models.dao.DbSettingDto;

public interface DbSettingRepo extends JpaRepository<DbSettingDto, Long> {
    // Optional<DbSettingDto> getFirstByKey(String propertyName);

    // Set<DbSettingDto> getByType(DbSettingDto type);
}
