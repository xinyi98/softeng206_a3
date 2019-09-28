package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Stage _thisStage;
	private Scene _mainMenu, _viewMenu, _createMenu;
	private SearchScene _searchScene;
	private String _keyword;
	private String _path;

	@Override
	public void start(Stage primaryStage) {
		try {

			// make a directory in user's home directory called "206a3_team17" to store all
			// the files created
			String myDirectory = "206a3_team17";
			String users_home = System.getProperty("user.home");
			String _path = users_home.replace("\\", "/") + File.separator + myDirectory;
			new File(_path).mkdir();

			/**
			 * need to clear every thing unnecessary generated before
			 */

			_thisStage = primaryStage;
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 750, 750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Generate Screens
			generateMain();

			primaryStage.setTitle("VARpedia");
			primaryStage.setScene(_mainMenu);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	
	public void switchScene(Scene newScene) {
		_thisStage.setScene(newScene);
	}

	public void generateMain() {
		Label mainMenuInfo = new Label(
				"Welcome to Group 17's Wiki-Speak Authoring Tool\nPlease select from one of the following options below");
		mainMenuInfo.setTextAlignment(TextAlignment.CENTER);
		Button viewBtn = new Button("View");
		viewBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				generateView();
				_thisStage.setScene(_viewMenu);
			}
		});
		Button createBtn = new Button("Create");
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {

				generateCreate(); 
				_thisStage.setScene(_createMenu);

			}
		});
		VBox mainMenu = new VBox(4);
		mainMenu.setAlignment(Pos.CENTER);
		mainMenu.getChildren().addAll(mainMenuInfo, viewBtn, createBtn);
		this._mainMenu = new Scene(mainMenu, 750, 750);
	}

	public void generateView() {
		BorderPane viewMenu = new BorderPane();
		TextArea text = new TextArea();
		viewMenu.setTop(text);

		this._viewMenu = new Scene(viewMenu, 500, 400);

	}

	public void generateCreate() {

		_searchScene = new SearchScene(this);
		_createMenu = _searchScene.getScene();

	}

	public void returnToMain() {
		_thisStage.setScene(_mainMenu);
	}

	
	
	public void clearFolder(String path) {
		
		// delete all the .txt and .wav files
		
		try {
			String cmd = "rm -fv " + path + "/" + "*.txt";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
			Process process = pb.start();
			
			
			String cmd1 = "rm -fv " + path + "/" + "*.wav";
			ProcessBuilder pb1 = new ProcessBuilder("bash", "-c", cmd1);
			Process process1 = pb1.start();

		} catch (Exception e) {
		
		}
		
	}
	
	
	
	public void setKeyword(String keyword) {
		_keyword = keyword;
	}
	
	public String getKeyword() {
		return _keyword;
	}
	
}
