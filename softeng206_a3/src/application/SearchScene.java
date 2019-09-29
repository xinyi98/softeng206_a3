package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SearchScene {

	private String _keyword;
	private String _path;
	private Main _stage;
	private List<String> _fileNames;
	private AudioScene _audioScene;
	private Scene _audioMenu;

	// default constructor
	public SearchScene(Main stage) {
		_stage = stage;
		String myDirectory = "206a3_team17"; // user Folder Name
		String users_home = System.getProperty("user.home");
		_path = users_home.replace("\\", "/") + File.separator + myDirectory;
		

	}

	// construct the scene for search the keyword in wiki
	public Scene getScene() {

		BorderPane root = new BorderPane();
		Scene searchScene = new Scene(root, 750, 750);

		// add the button "back to main menu"
		Button BackBtn = new Button("Back To Main Menu");
		BackBtn.setPrefSize(250, 40);
		BackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_stage.clearFolder(_path);
				_stage.returnToMain();
			}
		});

		// add the button "make a creation"
		Button CreateBtn = new Button("Make A Creation Now");
		CreateBtn.setPrefSize(250, 40);
		// enter the name for search
		ProgressBar progressBar = new ProgressBar(0);
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText("Enter A Keyword");
		td.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// show the text input dialog
				td.showAndWait();
				String ToSearch = td.getEditor().getText();

				if (ToSearch.isEmpty() || ToSearch.trim().isEmpty()) {

					// show alert
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setContentText("please enter a valid input!");
					alert.showAndWait();

				} else {
					
					_keyword = ToSearch;
					_stage.setKeyword(_keyword);

					// multi threads
					WikiSearch task = new WikiSearch(ToSearch);

					// when the thread is running
					task.setOnRunning((succeesesEvent) -> {
						Label b = new Label("Searching...please wait");
						// create a Stack pane
						StackPane r = new StackPane();
						// add password field
						r.getChildren().add(b);
						root.setLeft(r);
						// disable the button for creation
						CreateBtn.setDisable(true);
					});

					progressBar.progressProperty().bind(task.progressProperty());
					root.setCenter(progressBar);
					progressBar.setPrefWidth(100);

					// when the thread finished its task
					task.setOnSucceeded((succeededEvent) -> {
						try {
							CreateBtn.setDisable(false);

							String cmd = "cat " + _path + "/" + "textFromWiki.txt";
							ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
							Process process = pb.start();
							InputStream stdout = process.getInputStream();
							BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
							String line = stdoutBuffered.readLine();
							String temp = _keyword + " not found :^(";

							progressBar.progressProperty().unbind();
							progressBar.setProgress(0);

							if (line.equals(temp)) {
								// invalid input -- show alert
								Alert a = new Alert(AlertType.NONE);
								a.setAlertType(AlertType.ERROR);
								a.setContentText("NO RESULT (please enter a valid input!)");
								a.show();
							} else {
								// show that is a valid input
								Alert alert = new Alert(AlertType.CONFIRMATION, "Searching is done, want to continue?",
										ButtonType.YES, ButtonType.CANCEL);
								
								alert.showAndWait();
								if (alert.getResult() == ButtonType.YES) {
									/**
									 * go to the next scene -- create audio
									 */
									_audioScene = new AudioScene(_stage);
									_audioMenu = _audioScene.getScene();
									_stage.switchScene(_audioMenu);
									
								}
							}

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					});

					ExecutorService executorService = Executors.newFixedThreadPool(1);
					executorService.execute(task);
					executorService.shutdown();
				}

			}
		};

		CreateBtn.setOnAction(event);

		
		
		// show the list of existing creation
		try {
			String cmd = "ls " + _path + "/" + " | grep mp4 | sort | cut -f1 -d'.'";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
			Process process = pb.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			List<String> fileNames = new ArrayList<String>();
			String line = null;
			while ((line = stdoutBuffered.readLine()) != null) {
				fileNames.add(line);
			}
			_fileNames = fileNames;
		} catch (IOException e) {
		
		}
		
		ListView<String> list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableList(_fileNames);
		list.setItems(items);
		list.setOrientation(Orientation.VERTICAL);
		list.setPrefWidth(350);
		list.setPrefHeight(100);
		HBox hbox = new HBox(list);
		
		root.setRight(hbox);
		root.setTop(BackBtn);
		root.setBottom(CreateBtn);
		return searchScene;
	}

	
	public String getKeyword() {
		return this._keyword;
	}

	
	
}
