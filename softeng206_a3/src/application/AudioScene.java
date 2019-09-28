package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AudioScene {

	private Main _stage;
	private String _path;
	private String _selectedText = "";
	private String _text = "";
	private BorderPane _root;
	private ListView<String> _listView;

	public AudioScene(Main stage) {
		_root = new BorderPane();
		_listView = new ListView<String>();
		_listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		_stage = stage;
		String myDirectory = "206a3_team17"; // user Folder Name
		String users_home = System.getProperty("user.home");
		_path = users_home.replace("\\", "/") + File.separator + myDirectory;
	}

	public Scene getScene() {

		Scene audioScene = new Scene(_root, 750, 750);
		TextMenu();
		return audioScene;
	}

	public void TextMenu() {

		_root.getChildren().clear();
		_selectedText = "";

		// add the text area to show the selected text
		TextArea textArea1 = new TextArea();
		textArea1.setEditable(false);
		textArea1.setWrapText(true);

		TextArea textArea2 = new TextArea();
		textArea2.setWrapText(true);
		HBox textContainer = new HBox(40);
		textContainer.setMaxHeight(400);
		// textContainer.setPrefHeight(400);
		textContainer.setMaxWidth(650);
		// textContainer.setPrefWidth(600);
		textContainer.getChildren().addAll(textArea1, textArea2);

		// copy the content of "textFromWiki.txt" into the left text area.
		try {
			String cmd = "cat " + _path + "/" + "textFromWiki.txt";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
			Process process = pb.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			while ((line = stdoutBuffered.readLine()) != null) {
				_text = _text + line + "\n";
			}
		} catch (IOException e) {

		}
		textArea1.setText(_text);

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

		// add the button "preview" and "save"
		Button preview = new Button("Preview");
		preview.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// play the selected text

			} 
		});

		Button save = new Button("O K!");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (textArea2.getText().isEmpty() || textArea2.getText().trim().isEmpty()) {
					Alert a = new Alert(AlertType.NONE);
					a.setAlertType(AlertType.ERROR);
					a.setContentText("Please select/enter some text into the RIGHT text area.....");
					a.show();	
				}else {
					
					try {
						// save it as a text file
						File file = new File(_path + "/" + "SelectedText.txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						bw.write(textArea2.getText());
						bw.close();
					}catch (Exception e) {
						
					}
					// show confirmation
					Alert alert = new Alert(AlertType.CONFIRMATION, "Want to go to next step?", ButtonType.YES, ButtonType.NO);
					alert.showAndWait();
					if (alert.getResult() == ButtonType.YES) {
						// go to next scene
						CombineMenu();
					}
				}
				
			}
		});

		Button add = new Button("Add text");
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_selectedText = _selectedText + textArea1.getSelectedText();
				textArea2.setText(_selectedText);

			}
		});
		HBox BtnContainer = new HBox(40);
		BtnContainer.getChildren().addAll(preview, add, save);

		_root.setCenter(textContainer);
		_root.setTop(BackBtn);
		_root.setBottom(BtnContainer);
	}

	public void CombineMenu() {

		
		// GUI setup
		_root.getChildren().clear();
		HBox TopContainer = new HBox(60);
		HBox BtnContainer = new HBox(40);
		HBox Settings = new HBox(30);
		Settings.setMaxHeight(400);
		Settings.setMaxWidth(650);
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText("Enter a name for this");
		
		// list of existing audio files
		refreshList();

		// choose the speech synthesizers
		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.getItems().add("Default Synthesizer");
		choiceBox.getItems().add("Choice 2");
		choiceBox.getItems().add("Choice 3");
		choiceBox.setValue("Default Synthesizer");
		// enter the name for this audio

		Label note = new Label("[ Press 'Ctrl' to select multiple files ]");
		Button back = new Button("Back to add more audio files");
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// go to previous scene
				TextMenu();
			}
		});

		Button combine = new Button("Combine");
		combine.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ObservableList<String> selectedItems = _listView.getSelectionModel().getSelectedItems();
				// must be multiple selection
				for (String s : selectedItems) {

				}
				// combine muliple audio files
				
				
				
				
				
				
				
				
				
				refreshList();
			}
		});

		Button select = new Button("Select one audio & Go to next step");
		select.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// select the one u want to include in your final creation
				ObservableList<String> selectedItems = _listView.getSelectionModel().getSelectedItems();
				int count = 0 ;
				for (String s : selectedItems) {
					count++;
				}
				
				if (count == 1) {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Want to go to next step?", ButtonType.YES, ButtonType.NO);
					alert.showAndWait();
					if (alert.getResult() == ButtonType.YES) {
						// go to next scene
						
						
						
						
						
						
						
					}
					
				}else {
					Alert a = new Alert(AlertType.NONE);
					a.setAlertType(AlertType.ERROR);
					a.setContentText("Sorry, please select one audio to make a creation");
					a.show();
				}
				
			}
		});

		Button delete = new Button("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				ObservableList<String> selectedItems = _listView.getSelectionModel().getSelectedItems();

				if (selectedItems.isEmpty()) {
					Alert a = new Alert(AlertType.NONE);
					a.setAlertType(AlertType.ERROR);
					a.setContentText("Sorry, u have selected nothing :(");
					a.show();
				}

				for (String s : selectedItems) {
					try {
						String cmd = "rm " + _path + "/" + s + ".wav";
						ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
						Process process = pb.start();
					} catch (IOException e) {

					}
				}
				refreshList();
			}
		});

		Button name = new Button("Create a new audio");
		name.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String synthesizer = choiceBox.getValue();
				
				td.showAndWait();
				String AudioName = td.getEditor().getText();
				
				// determine whether it is a valid name
				if(AudioName.isEmpty() || AudioName.trim().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setContentText("please enter a valid input!");
					alert.showAndWait();
				}else {
					
					if (IsValidName(AudioName) ) {
						// create the audio
						if ( synthesizer == "Choice 2" ) {
							try {
								String cmd = "espeak -vaf " + _path + "/" + "SelectedText.txt -w " + _path + "/" + AudioName +".wav";
								ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
								Process process = pb.start();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if (synthesizer == "" ) {
							
						}else {
							// use the default synthesizer
							try {
								String cmd = "espeak -f " + _path + "/" + "SelectedText.txt -w " + _path + "/" + AudioName +".wav";
								ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
								Process process = pb.start();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						// show confirmation 
						Alert alert = new Alert(AlertType.CONFIRMATION, "The audio " + AudioName + " is made", ButtonType.OK);
						alert.showAndWait();

					}else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setContentText("please enter another name!");
						alert.showAndWait();
					}
		
				}
				refreshList();
			}
		});

		Settings.getChildren().addAll(_listView, choiceBox, name);
		// BtnContainer.getChildren().addAll(combine, select, delete);
		BtnContainer.getChildren().addAll(delete, combine, select);
		TopContainer.getChildren().addAll(back, note);
		_root.setCenter(Settings);
		_root.setTop(TopContainer);
		_root.setBottom(BtnContainer);

	}

	public List<String> getNameList() {
		try {
			String cmd = "ls " + _path + "/" + " | grep wav | sort | cut -f1 -d'.'";
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
		} catch (IOException e) {
			return null;
		}
	}

	public void refreshList() {
		List<String> fileNames = getNameList();
		ObservableList<String> items = FXCollections.observableList(fileNames);
		_listView.setItems(items);
	}
	
	
	public boolean IsValidName(String InputName) {
		boolean IsExist = false;
		List<String> fileNames = getNameList();
		for (int i = 0; i < fileNames.size(); i++) {
			if (InputName.equals(fileNames.get(i))) {
				IsExist = true;
				break;
			}
		}
		if (IsExist == false) {
			return true;
		} else {
			return false;
		}

	}

}
