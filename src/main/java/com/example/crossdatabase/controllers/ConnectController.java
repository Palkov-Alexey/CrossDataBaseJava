package com.example.crossdatabase.controllers;

import com.example.crossdatabase.annotations.DbTypes;
import com.example.crossdatabase.data_access.TestConnections;
import com.example.crossdatabase.data_access.infrastructure.Driver;
import com.example.crossdatabase.enums.*;
import com.example.crossdatabase.helpers.AnnotationHelper;
import com.example.crossdatabase.models.DbSettingModel;
import com.example.crossdatabase.models.controllers.DbAuthDto;
import com.example.crossdatabase.models.controllers.DbTypeDto;
import com.example.crossdatabase.sevices.DbSettingService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Controller
@FxmlView("/views/ConnectWindow.fxml")
public class ConnectController {
    private final TestConnections testConnections;

    private final DbSettingService dbSettingService;

    public DbSettingModel dbSetting = new DbSettingModel();

    private Stage stage;

    public VBox connectWindow;

    @FXML
    GridPane loginAndPassBlock;

    @FXML
    ChoiceBox<DbTypeDto> dbType;

    @FXML
    ChoiceBox<DbAuthDto> authType;

    @FXML
    TextField name;

    @FXML
    TextField host;

    @FXML
    TextField instance;

    @FXML
    TextField port;

    @FXML
    TextField login;

    @FXML
    PasswordField password;

    @FXML
    Label testText;

    public ConnectController(TestConnections testConnections, DbSettingService dbSettingService) {
        this.testConnections = testConnections;
        this.dbSettingService = dbSettingService;
    }

    @FXML
    protected void selectBaseType() throws Exception {
        var type = dbType.getValue();
        getAuthTypes(type.getType());
    }

    protected void getAuthTypes(DbType type) throws Exception {
        type.toString();
        var allAuthTypes = DbAuthenticationType.values();
        var authTypes = new ArrayList<DbAuthDto>();

        for (var allAuthType : allAuthTypes) {
            var annotation = AnnotationHelper.getAnnotation(allAuthType, DbTypes.class);
            if (annotation.isPresent()) {
                var types = annotation.get().types();
                if (Arrays.stream(types).anyMatch(t -> t == type)) {
                    authTypes.add(new DbAuthDto(allAuthType));
                }
            }
        }

        if (!authTypes.isEmpty()) {

            authType.setItems(FXCollections.observableArrayList(authTypes));
            authType.setValue(authTypes.getFirst());
        }
    }

    @FXML
    protected void selectAuthType() {
        var value = authType.getValue();
        if (value != null) {
            loginAndPassBlock.setVisible(authType.getValue().getType() == DbAuthenticationType.LoginAndPassword);
        }
    }

    @FXML
    protected void setSetting() {
        dbSetting.setName(name.getText());
        dbSetting.setDbType(dbType.getValue().getType());
        dbSetting.setHost(host.getText());
        dbSetting.setInstance(instance.getText());
        dbSetting.setPort(port.getText());
        dbSetting.setAuthType(authType.getValue().getType());
        dbSetting.setLogin(login.getText());
        dbSetting.setPassword(password.getText());
        dbSetting.setUrl(Driver.getUrl(dbSetting, null));
    }

    @FXML
    protected void test() {
        setSetting();

        var test_result = testConnections.test(dbSetting);
        if (Objects.requireNonNull(test_result) == StatusType.Success) {
            testText.setText("Ok");
            testText.setStyle("-fx-text-fill: green");
        } else {
            testText.setText("Error");
            testText.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    public void initialize() {
        try {
            var types = Arrays.asList(DbType.values()).stream().map(x->new DbTypeDto(x)).toList();
            dbType.setItems(FXCollections.observableArrayList(types));
            dbType.setValue(types.getFirst());

            selectBaseType();

            this.stage = new Stage();
            stage.setScene(new Scene(connectWindow));
            stage.setResizable(false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void show() {
        stage.show();
    }

    @FXML
    protected void onSave() {
        setSetting();

        dbSettingService.saveSetting(dbSetting);
        stage.close();
    }

    @FXML
    protected void onCancel() {
        Stage stage = (Stage) authType.getScene().getWindow();
        stage.close();
    }
}
