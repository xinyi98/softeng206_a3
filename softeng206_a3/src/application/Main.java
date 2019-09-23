package application;

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


	@Override
	public void start(Stage primaryStage) {
		try {
			_thisStage = primaryStage;

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			//Generate Screens
			generateMain();
			generateView();
			generateCreate();


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

	public void switchScene(Scene newScene){
		_thisStage.setScene(newScene);
	}

	public void generateMain(){
		Label mainMenuInfo = new Label("Welcome to Group 17's Wiki-Speak Authoring Tool\nPlease select from one of the following options below");
		mainMenuInfo.setTextAlignment(TextAlignment.CENTER);
		Button viewBtn = new Button("View");
		viewBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				_thisStage.setScene(_viewMenu);
			}
		});
		Button createBtn = new Button("Create");
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
                _thisStage.setScene(_createMenu);
			}
		});
		VBox mainMenu = new VBox(4);
		mainMenu.setAlignment(Pos.CENTER);
		mainMenu.getChildren().addAll(mainMenuInfo, viewBtn, createBtn);
		this._mainMenu = new Scene(mainMenu, 500, 400);
	}

	public void generateView(){
		BorderPane viewMenu = new BorderPane();
		TextArea text = new TextArea();
		viewMenu.setTop(text);

		this._viewMenu = new Scene(viewMenu, 500, 400);

	}

	public void generateCreate(){
		//create the creation menu here

	}

}
