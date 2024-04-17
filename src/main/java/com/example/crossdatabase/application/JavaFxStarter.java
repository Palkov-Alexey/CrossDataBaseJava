package com.example.crossdatabase.application;

import com.example.crossdatabase.Main;
import com.example.crossdatabase.events.StageReadyEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxStarter extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.context = new SpringApplicationBuilder()
                .sources(Main.class)
                .headless(false)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        this.context.close();
        Platform.exit();
        System.exit(0);
    }
}