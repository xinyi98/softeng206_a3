package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.File;

public class viewScene {
    private Scene _viewScene;
    private application.Main _stage;
    private String _path;

    public viewScene(Main stage){
        _stage = stage;
        String myDirectory = "206a3_team17"; // user Folder Name
        String users_home = System.getProperty("user.home");
        _path = users_home.replace("\\", "/") + File.separator + myDirectory;
        generateView();
    }

    public void generateView(){
        BorderPane viewMenu = new BorderPane();
        VBox viewMenuHolder = new VBox(4);
        VBox options = new VBox(4);
        Button playBtn = new Button("Play");
        Button deleteBtn = new Button("Delete");

        VBox deleteOptions = new VBox(4);
        Label confirmMsg = new Label("Are you sure you want to do this?");
        Button cancelBtn = new Button("Cancel");
        Button confirmBtn = new Button("Confirm");
        deleteOptions.getChildren().addAll(confirmMsg, cancelBtn, confirmBtn);
        deleteOptions.setAlignment(Pos.CENTER);

        Label listViewTitle = new Label("Creations:");
        listViewTitle.setTextAlignment(TextAlignment.CENTER);
        listViewTitle.setTextAlignment(TextAlignment.CENTER);

        ListView creationList = new ListView();
        creationList.setOrientation(Orientation.VERTICAL);

        viewMenuHolder.getChildren().addAll(listViewTitle, creationList);
        viewMenuHolder.setAlignment(Pos.CENTER);
        options.getChildren().addAll(playBtn, deleteBtn);
        options.setAlignment(Pos.CENTER);

        Button viewBackBtn = new Button("Back");
        viewBackBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                _stage.returnToMain();
            }
        });

        viewMenu.setTop(viewBackBtn);
        viewMenu.setCenter(viewMenuHolder);
        viewMenu.setBottom(options);

        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String selectedFile = creationList.getSelectionModel().getSelectedItem().toString();

                    String cmd = "ffplay -autoexit " + selectedFile + ".mp4 &> /dev/null";
                    ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
                    Process process = builder.start();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    viewMenu.setBottom(deleteOptions);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                try {
//                    String selectedFile = creationList.getSelectionModel().getSelectedItem().toString();
//
//                    String cmd = "rm " + selectedFile + ".mp4 &> /dev/null";
//                    System.out.println(selectedFile);
//                    ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
//                    Process process = builder.start();
//
//                    list.remove(creationList.getSelectionModel().getSelectedItem());
//                    creationList.getItems().clear();
//                    creationList.getItems().addAll(list);
//
//                    viewMenu.setBottom(options);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        this._viewScene = new Scene(viewMenu, 750, 750);

    }

    public Scene getScene(){
        return _viewScene;
    }
}
