package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Stage _thisStage;
	private Scene _mainMenu, _viewMenu, _createMenu;
<<<<<<< HEAD
	private SearchScene _searchScene;
	private String _keyword;
	private String _path;
	private String _audio;
=======

	private viewScene _viewControl;

>>>>>>> master

	@Override
	public void start(Stage primaryStage) {
		try {

			// make a directory in user's home directory called "206a3_team17" to store all
			// the files created
			String myDirectory = "206a3_team17";
			String users_home = System.getProperty("user.home");
			_path = users_home.replace("\\", "/") + File.separator + myDirectory;
			new File(_path).mkdir();

			// generate .scm file for festival
			GenerateScm();

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

		final double MAX_FONT_SIZE = 20.0; // define max font size you need
		mainMenuInfo.setFont(new Font(MAX_FONT_SIZE));
		Button viewBtn = new Button("View");
		viewBtn.setPrefSize(150, 40);
		viewBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
<<<<<<< HEAD
				generateView();
=======
				_viewControl.refreshList();
>>>>>>> master
				_thisStage.setScene(_viewMenu);
			}
		});
		Button createBtn = new Button("Create");
		createBtn.setPrefSize(150, 40);
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {

				generateCreate();
				_thisStage.setScene(_createMenu);

			}
		});
		VBox mainMenu = new VBox(40);
		mainMenu.setAlignment(Pos.CENTER);
		mainMenu.getChildren().addAll(mainMenuInfo, viewBtn, createBtn);
		this._mainMenu = new Scene(mainMenu, 750, 750);
	}

<<<<<<< HEAD
	public void generateView() {
		BorderPane viewMenu = new BorderPane();
		TextArea text = new TextArea();
		viewMenu.setTop(text);

		this._viewMenu = new Scene(viewMenu, 500, 400);
=======
	public void generateView(){
		_viewControl = new viewScene(this);
		_viewMenu = _viewControl.getScene();
>>>>>>> master

	}

	public void generateCreate() {

		_searchScene = new SearchScene(this);
		_createMenu = _searchScene.getScene();

	}

	public void returnToMain() {
		_thisStage.setScene(_mainMenu);
	}

<<<<<<< HEAD
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

	public void setAudio(String str) {
		_audio = str;
	}

	public String getAudio() {
		return _audio;
	}

	public void GenerateScm() throws IOException {
		FileWriter fw1 = new FileWriter(_path + "/" + "akl.scm");
		fw1.write("(voice_akl_nz_jdt_diphone)");
		fw1.close();

		FileWriter fw2 = new FileWriter(_path + "/" + "kal.scm");
		fw2.write("(voice_kal_diphone)");
		fw2.close();

	}
=======
	public void returnToMain(){
		_thisStage.setScene(_mainMenu);
	}

	public void goToView(){
		_thisStage.setScene(_viewMenu);
	}

>>>>>>> master
}
