package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class AudioScene {

	private Main _stage;
	private String _path;

	public AudioScene(Main stage) {
		_stage = stage;
		String myDirectory = "206a3_team17"; // user Folder Name
		String users_home = System.getProperty("user.home");
		_path = users_home.replace("\\", "/") + File.separator + myDirectory;
	}

	public Scene getScene() {
		
		BorderPane root = new BorderPane();
		Scene audioScene = new Scene(root, 750, 750);
		
		// add the button "back to main menu"
		Button BackBtn = new Button("Back To Main Menu");
		BackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			// clear all the unnecessary file
				_stage.clearFolder(_path);
				_stage.returnToMain();
			}
		});
		
		TextArea textArea1 = new TextArea();
		TextArea textArea2 = new TextArea();
        HBox textContainer = new HBox(40);
        textContainer.setMaxHeight(400);
        //textContainer.setPrefHeight(400);
        textContainer.setMaxWidth(650);
        //textContainer.setPrefWidth(600);
        textContainer.getChildren().addAll(textArea1, textArea2);
      
        
        
        
        
        
        
		root.setCenter(textContainer);
		root.setTop(BackBtn);
		return audioScene;
	}
}
