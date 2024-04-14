package com.example.crossdatabase;

import com.example.crossdatabase.application.JavaFxStarter;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Application.launch(JavaFxStarter.class, args);
    }
}
