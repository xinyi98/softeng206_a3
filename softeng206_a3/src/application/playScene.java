package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class playScene {
    private Scene _playScene;
    private String _path;
    private Main _stage;

    public playScene(String path, Main stage){
        _path = path;
        _stage = stage;
    }



    public void generatePlay(String fileName){
        String path = _path + "/" + fileName + ".mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        // need to make the video stretch/fill the screen
        mediaPlayer.setAutoPlay(true);
        BorderPane root = new BorderPane();
        Button returnBtn = new Button("Return");
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                _stage.goToView();
            }
        });
        root.setCenter(mediaView);
        root.setBottom(returnBtn);
        root.setAlignment(returnBtn, Pos.CENTER);
        this._playScene = new Scene(root, 750, 750);
    }

    public Scene getScene(){
        return this._playScene;
    }

}
