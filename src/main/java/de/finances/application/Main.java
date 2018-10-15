package de.finances.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("fxml/Main.fxml"));

			Scene scene = new Scene(root, 400, 400);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			scene.getStylesheets().add(this.getClass().getClassLoader().getResource("css/styles.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
