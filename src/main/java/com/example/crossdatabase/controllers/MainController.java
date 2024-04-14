package com.example.crossdatabase.controllers;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Controller;

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
public class MainController {
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
    private TreeView<String> dbList;

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
        try {
            var engines = dbSettingService.getEngines();
            var rootItem = new TreeItem<String>("Servers");

            for (var engine : engines) {
                var schems = engine.getSchema();
                var serverItem = new TreeItem<String>(engine.getName());

                if (schems.size() > 0) {
                    Set<Entry<String, List<DataBaseModel>>> catalogsGroups = schems.stream()
                            .collect(Collectors.groupingBy(DataBaseModel::getTableCatalog,
                                    () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER),
                                    Collectors.toList()))
                            .entrySet();

                    for (var catalog : catalogsGroups) {
                        var catalogItem = new TreeItem<String>(catalog.getKey());

                        Set<Entry<String, List<String>>> schemaGroups = catalog.getValue().stream()
                                .collect(Collectors.groupingBy(x -> x.getTableSchema(),
                                        () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER),
                                        Collectors.mapping(x -> x.getTableName(), Collectors.toList())))
                                .entrySet();

                        for (var group : schemaGroups) {
                            var schemaItem = new TreeItem<String>(group.getKey());
                            var tables = group.getValue().stream().sorted(String.CASE_INSENSITIVE_ORDER).toList();

                            for (var table : tables) {
                                schemaItem.getChildren().add(new TreeItem<String>(table));
                            }

                            catalogItem.getChildren().add(schemaItem);
                        }

                        serverItem.getChildren().add(catalogItem);
                    }
                }

                rootItem.getChildren().add(serverItem);
            }
            
            dbList.setRoot(rootItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}