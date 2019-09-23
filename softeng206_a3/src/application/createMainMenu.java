package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Collections;
import java.util.Map;

public class createMainMenu {
    private Main _mainStage;
    private Scene _mainMenu;

    public createMainMenu(Main application){
        this._mainStage = application;
    }

    public void generateScreen(){
        Label mainMenuInfo = new Label("Welcome to bche536's Wiki-Speak Authoring Tool\nPlease select from one of the following options below");
        mainMenuInfo.setTextAlignment(TextAlignment.CENTER);
        Button viewBtn = new Button("View");
        viewBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Collections.sort(list);
                primaryStage.setScene(view);
            }
        });
        Button createBtn = new Button("Create");
        createBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(create);
            }
        });
        VBox mainMenu = new VBox(4);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.getChildren().addAll(mainMenuInfo, viewBtn,createBtn);
        mainScene = new Scene(mainMenu, 500, 400);
    }

}
