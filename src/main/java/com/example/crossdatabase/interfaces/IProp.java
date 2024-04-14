package com.example.crossdatabase.interfaces;

import com.example.crossdatabase.enums.SysPropType;

public interface IProp {
    String getDescription();
    
    String getDefaultValue();
        
    String name();

    SysPropType getSysPropType();
}
