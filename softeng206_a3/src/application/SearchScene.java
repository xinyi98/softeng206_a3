package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class SearchScene {

	private String _keyword;
    private String _path;
    
	// default constructor
	public SearchScene() {
		String myDirectory = "206a3_team17"; // user Folder Name
		//String path = getUsersHomeDir() + File.separator + myDirectory;
		String users_home = System.getProperty("user.home");
		_path = users_home.replace("\\", "/") + File.separator + myDirectory;
		
	}
	

	// construct the scene for search the keyword in wiki
	public Scene SwichToSearchScene() {
		
		
		
		BorderPane root = new BorderPane();
		Scene SearchScene = new Scene(root, 400, 400);

		// add the button "back to main menu"
		Button BackBtn = new Button("Back To Main Menu");
		BackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/**
				 * switch to main menu
				 */
			}
		});

		
		// add the button "make a creation"
		Button CreateBtn = new Button("Make A Creation Now");
		
		// enter the name for search
		ProgressBar progressBar = new ProgressBar(0);
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText("Enter something u want to search");
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
					WikiSearch ws = new WikiSearch(ToSearch);
					Thread thread = new Thread(ws);
					thread.start();
					_keyword = ToSearch;

					// multi threads
					WikiSearch task = new WikiSearch(ToSearch);
					
					
					//when the thread is running 
					task.setOnRunning((succeesesEvent) -> {
						Label b = new Label("Searching...please wait");
						// create a Stack pane
						StackPane r = new StackPane();
						// add password field
						r.getChildren().add(b);
						root.setLeft(r);
						//disable the button for creation
						CreateBtn.setDisable(true); 
					});

					progressBar.progressProperty().bind(task.progressProperty());
					root.setCenter(progressBar);
					progressBar.setPrefWidth(40);
					
					
					//when the thread finished its task
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
		
		root.setTop(BackBtn);
		root.setBottom(CreateBtn);
		
		return SearchScene;
	}

	public String getKeyword() {
		return this._keyword;
	}

}
