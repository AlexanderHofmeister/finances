package de.finances.view.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainController {

	@FXML
	private Pane mainPane;

	private void loadEntityOverview(final String name) throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/" + name + ".fxml"));
		final Pane pane = (Pane) loader.load();
		this.mainPane.getChildren().clear();
		this.mainPane.getChildren().add(pane);
	}

	public void showFinances() throws IOException {
		loadEntityOverview("financesOverview");
	}

	public void exitApplication() {
		Platform.exit();
	}

}
