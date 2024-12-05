package com.example.crossdatabase.models.dao;


import com.example.crossdatabase.enums.SysPropType;
import jakarta.persistence.*;

@Entity
@Table(name = "sys_prop")
public class SysProp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sys_key")
    private String sysKey;

    @Enumerated(EnumType.STRING)
    private SysPropType type;

    @Lob
    private String value;
    
    @Column(name = "description")
    private String description;
}
