package de.finances.view.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainController {

	@FXML
	private Pane mainPane;

	public void exitApplication() {
		Platform.exit();
	}

	private void loadEntityOverview(final String name) throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getClassLoader().getResource("fxml/" + name + ".fxml"));
		final Pane pane = (Pane) loader.load();
		this.mainPane.getChildren().clear();
		this.mainPane.getChildren().add(pane);
	}

	public void showFinances() throws IOException {
		this.loadEntityOverview("transactionOverview");
	}

}
