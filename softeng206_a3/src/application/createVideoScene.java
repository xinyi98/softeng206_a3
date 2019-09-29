package application;

import com.flickr4java.flickr.Flickr;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class createVideoScene {
    private Main _stage;
    private String _path, _creationName, _result;
    private Scene _createVideoScene;
    private ProgressBar _flickrBar, _progressBar;
    private ExecutorService _flickrService;
    public boolean _completed;

    public createVideoScene(Main stage) {
        _stage = stage;
        String myDirectory = "206a3_team17"; // user Folder Name
        String users_home = System.getProperty("user.home");
        _path = users_home.replace("\\", "/") + File.separator + myDirectory;
        generateCreateVideo();
    }

    public void generateCreateVideo(){
        Label createVideoMenuInfo = new Label("Please enter a number (in the range 1-10 inclusively) for the number of images you would like in your slideshow");
        createVideoMenuInfo.setTextAlignment(TextAlignment.CENTER);
        TextField input = new TextField();
        Button okBtn = new Button("Ok");
        Button cancelBtn = new Button ("Cancel and return to main menu");
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                _stage.returnToMain();
            }
        });
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    int num = Integer.parseInt(input.getText());
                    if (num > 10 || num < 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("You have entered an invalid input");
                        alert.setContentText("Please enter a number in the valid range 1-10 inclusively");
                        alert.showAndWait();
                    }
                    else {
                        flickrClass flickrTask = new flickrClass("dog", num);
                        _flickrService = Executors.newSingleThreadExecutor();
                        _flickrService.submit(flickrTask);

                        flickrTask.setOnRunning((succeesesEvent) -> {
                            _flickrBar = new ProgressBar(0);
                            _flickrBar.progressProperty().bind(flickrTask.progressProperty());
                            _flickrBar.setPrefWidth(40);

                            Label downloadingLabel = new Label("Retrieving images please wait...");

                            VBox createVideoMenu = new VBox(4);
                            createVideoMenu.setAlignment(Pos.CENTER);
                            createVideoMenu.getChildren().addAll(downloadingLabel, _flickrBar);
                            Scene loadingScene = new Scene(createVideoMenu, 750, 750);
                            _stage.switchScene(loadingScene);
                        });

                        _completed = false;

                            // when the thread finished its task
                            flickrTask.setOnSucceeded((succeededEvent) -> {
                                while (!_completed) {
                                    try {
                                        TextInputDialog nameOfCreation = new TextInputDialog();
                                        nameOfCreation.setHeaderText("Please enter the name you want to name this file");
                                        nameOfCreation.showAndWait();
                                        _creationName = nameOfCreation.getEditor().getText() + ".mp4";
                                        if(_creationName.equals(".mp4")){
                                            _stage.switchScene(_createVideoScene);
                                            break;
                                        }

                                        String cmd = "test -e " + _path + "/" + _creationName;
                                        ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
                                        Process process = builder.start();
                                        process.waitFor();
                                        int exitVal = process.exitValue();

                                        if (exitVal == 1) {
                                            System.out.println("reach here");

                                            // multi threads
                                            createVideoTask task = new createVideoTask(_stage);

                                            // when the thread is runni)ng
                                            task.setOnRunning((succeesesEvent) -> {
                                                _progressBar = new ProgressBar(0);
                                                Label loadingLabel = new Label("Creating video file please wait...");

                                                VBox createVideoMenu = new VBox(4);
                                                createVideoMenu.setAlignment(Pos.CENTER);
                                                createVideoMenu.getChildren().addAll(loadingLabel, _progressBar);
                                                Scene loadingScene = new Scene(createVideoMenu, 750, 750);
                                                _stage.switchScene(loadingScene);
                                            });

                                            _progressBar.progressProperty().bind(task.progressProperty());
                                            _progressBar.setPrefWidth(40);

                                            // when the thread finished its task
                                            task.setOnSucceeded((EventSuccess) -> {
                                                try {
                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                    alert.setTitle("Creation successful");
                                                    alert.setHeaderText("Video creation was successful");
                                                    alert.setContentText("The file" + _creationName + " has successfully been created, click Ok to return to the main menu");
                                                    alert.showAndWait();
                                                    _completed = true;

                                                    String rmCmd = "rm -r " + _path + "/" + _creationName;
                                                    ProcessBuilder rmBuilder = new ProcessBuilder("bash", "-c", cmd);
                                                    Process rmProcess = builder.start();

//                                                    _stage.clearFolder();

                                                    _progressBar.progressProperty().unbind();
                                                    _progressBar.setProgress(0);
                                                } catch (Exception e1) {
                                                    e1.printStackTrace();
                                                }

                                            });

                                            ExecutorService executorService = Executors.newFixedThreadPool(1);
                                            executorService.execute(task);
                                            executorService.shutdown();

                                        } else {
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Invalid input");
                                            alert.setHeaderText("You have entered an invalid input");
                                            alert.setContentText("The name of the file you are trying to create already exists");
                                            alert.showAndWait();
                                        }


                                        _flickrBar.progressProperty().unbind();
                                        _flickrBar.setProgress(0);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
                    }
                }
                catch(NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid input");
                    alert.setHeaderText("You have entered an invalid input");
                    alert.setContentText("Please enter a number only");
                    alert.showAndWait();
                }
            }
        });
        BorderPane holder = new BorderPane();
        VBox createVideoMenu = new VBox(4);
        createVideoMenu.setAlignment(Pos.CENTER);
        createVideoMenu.getChildren().addAll(createVideoMenuInfo, input, okBtn);
        holder.setTop(cancelBtn);
        holder.setCenter(createVideoMenu);
        _createVideoScene = new Scene(holder, 750 ,750);
    }

    public Scene getScene(){
        return this._createVideoScene;
    }
}
