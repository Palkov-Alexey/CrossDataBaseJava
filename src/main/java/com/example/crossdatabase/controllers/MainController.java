package com.example.crossdatabase.controllers;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;

import com.example.crossdatabase.events.SqlEngineEvent;
import com.example.crossdatabase.helpers.IconHelper;
import com.example.crossdatabase.interfaces.ISqlEngine;
import com.example.crossdatabase.models.dao.DataBaseModel;
import com.example.crossdatabase.sevices.DbSettingService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("/views/MainWindow.fxml")
public class MainController implements ApplicationListener<SqlEngineEvent> {
    private final FxControllerAndView<ConnectController, AnchorPane> connectWindow;
    private final DbSettingService dbSettingService;

    public MainController(FxControllerAndView<ConnectController, AnchorPane> connectWindow,
            DbSettingService dbSettingService) {
        this.connectWindow = connectWindow;
        this.dbSettingService = dbSettingService;
    }

    private int tabIndex = 0;

    @FXML
    private TabPane tabs;

    @FXML
    private TreeView<Object> dbList;

    @FXML
    protected void addTab() {
        var tabName = tabIndex == 0 ? "New tab" : "New tab %d".formatted(tabIndex);
        ++tabIndex;
        var tab = new Tab(tabName);
        tab.setContent(new Pane());
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tabs.getTabs().getLast());
    }

    @FXML
    protected void onConnect() {
        connectWindow.getController().show();
    }

    @FXML
    protected void onClose() {
        Platform.exit();
    }

    @FXML
    public void initialize() {
        var rootItem = new TreeItem<Object>("Servers");
        dbList.setRoot(rootItem);
        var engines = dbSettingService.getEngines();

        for (var engine : engines) {
            buildServerList(engine);
        }
    }

    @Override
    public void onApplicationEvent(SqlEngineEvent event) {
        var engine = event.stage;
        buildServerList(engine);
    }

    private void buildServerList(ISqlEngine engine) {
        try {
            var schems = engine.getSchema();
            var serverItem = new TreeItem<Object>(engine, IconHelper.getPostgreSqlImage());

            if (schems.size() > 0) {
                Set<Entry<String, List<DataBaseModel>>> catalogsGroups = schems.stream()
                        .collect(Collectors.groupingBy(DataBaseModel::getTableCatalog,
                                () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER),
                                Collectors.toList()))
                        .entrySet();

                for (var catalog : catalogsGroups) {
                    var catalogItem = new TreeItem<Object>(catalog.getKey());

                    Set<Entry<String, List<String>>> schemaGroups = catalog.getValue().stream()
                            .collect(Collectors.groupingBy(x -> x.getTableSchema(),
                                    () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER),
                                    Collectors.mapping(x -> x.getTableName(), Collectors.toList())))
                            .entrySet();

                    for (var group : schemaGroups) {
                        var schemaItem = new TreeItem<Object>(group.getKey());
                        var tables = group.getValue().stream().sorted(String.CASE_INSENSITIVE_ORDER).toList();

                        for (var table : tables) {
                            schemaItem.getChildren().add(new TreeItem<Object>(table));
                        }

                        catalogItem.getChildren().add(schemaItem);
                    }

                    serverItem.getChildren().add(catalogItem);
                }
            }

            dbList.getRoot().getChildren().add(serverItem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}