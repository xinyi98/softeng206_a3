package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class viewScene {
    private Scene _viewScene;
    private playScene _playController;
    private application.Main _stage;
    private String _path;
    private ListView<String> _list;

    public viewScene(Main stage){
        _stage = stage;
        String myDirectory = "206a3_team17"; // user Folder Name
        String users_home = System.getProperty("user.home");
        _path = users_home.replace("\\", "/") + File.separator + myDirectory;
        generateView();
        _playController = new playScene(_path, _stage);

    }

    public void generateView(){
        BorderPane viewMenu = new BorderPane();
        VBox viewMenuHolder = new VBox(4);
        VBox options = new VBox(4);
        Button playBtn = new Button("Play");
        Button deleteBtn = new Button("Delete");
        Button refreshBtn = new Button("Refresh");

        Label listViewTitle = new Label("Creations:");
        listViewTitle.setTextAlignment(TextAlignment.CENTER);
        listViewTitle.setTextAlignment(TextAlignment.CENTER);

        _list = new ListView<String>();
        _list.setOrientation(Orientation.VERTICAL);

        viewMenuHolder.getChildren().addAll(listViewTitle, _list);
        viewMenuHolder.setAlignment(Pos.CENTER);
        options.getChildren().addAll(playBtn, deleteBtn, refreshBtn);
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
                    String selectedFile = _list.getSelectionModel().getSelectedItem().toString();
                    _playController.generatePlay(selectedFile);
                    _stage.switchScene(_playController.getScene());


//                    String cmd = "ffplay -autoexit " + _path + "/" + selectedFile + ".mp4 &> /dev/null";
//                    ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
//                    Process process = builder.start();
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
                    String selection = _list.getSelectionModel().getSelectedItem().toString();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selection + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        String cmd = "rm -f " + _path + "/" + selection + ".mp4";
                        ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
                        Process process = builder.start();
                        refreshList();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    refreshList();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        this._viewScene = new Scene(viewMenu, 750, 750);

    }

    public Scene getScene(){
        return _viewScene;
    }

    public List<String> getNameList(){
        try {
            String cmd = "ls " + _path + "/" + " | grep mp4 | sort | cut -f1 -d'.'";
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
            Process process = builder.start();
            InputStream stdout = process.getInputStream();
            BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
            List<String> fileNames = new ArrayList<String>();
            String line = null;
            while ((line = stdoutBuffered.readLine()) != null) {
                fileNames.add(line);
            }
            return fileNames;
        }
        catch (IOException e){
            return null;
        }
    }

    public void refreshList(){
        List<String> fileNames = getNameList();
        ObservableList<String> items = FXCollections.observableList(fileNames);
        _list.setItems(items);
    }
}
