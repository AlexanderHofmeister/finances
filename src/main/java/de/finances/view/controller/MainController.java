package de.finances.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {

	@FXML
	private Pane mainPane;

	public void exitApplication() {
		Platform.exit();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		try {
			this.showFinances();
		} catch (final IOException e) {
		}

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

	public void showStatistics() throws IOException {
		this.loadEntityOverview("statisticsOverview");
	}

}
