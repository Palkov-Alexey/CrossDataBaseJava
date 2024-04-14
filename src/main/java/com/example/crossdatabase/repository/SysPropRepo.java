package com.example.crossdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crossdatabase.models.dao.SysProp;

public interface SysPropRepo extends JpaRepository<SysProp, Long> {
    // Optional<SysProp> getFirstBySisKey(String propertyName);

    // Set<SysProp> getByType(SysProp type);
}
