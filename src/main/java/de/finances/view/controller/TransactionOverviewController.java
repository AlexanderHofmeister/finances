package de.finances.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.finances.application.model.Transaction;
import de.finances.application.model.TransactionType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class TransactionOverviewController implements Initializable {

	@FXML
	private Pane entityPane;

	@FXML
	private Button createExpenseButton;

	@FXML
	private Button createIncomeButton;

	private void buildTransactionEdit(final Transaction item) {

		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getClassLoader().getResource("fxml/transactionEdit.fxml"));
		Pane editPane = null;
		try {
			editPane = (Pane) loader.load();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final TransactionEditController transactionEditController = loader.getController();
		transactionEditController.setTransaction(item);
		transactionEditController.getCancel().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			this.loadTable();
		});
		transactionEditController.getSave().setOnAction(e -> {
			transactionEditController.saveTransaction();
			this.entityPane.getChildren().clear();
			this.loadTable();
		});
		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(editPane);

	}

	public void createExpense() {
		this.createTransaction(TransactionType.EXPENSE);
	}

	public void createIncome() {
		this.createTransaction(TransactionType.INCOME);
	}

	private void createTransaction(final TransactionType expense) {
		final Transaction item = new Transaction();
		item.setType(expense);
		this.buildTransactionEdit(item);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.loadTable();

	}

	private void loadTable() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getClassLoader().getResource("fxml/transactionTable.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final TransactionTableController controller = loader.getController();

		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(pane);

		final TableView<Transaction> transactionTable = controller.getTransactionTable();

		transactionTable.setRowFactory(tv -> {
			final TableRow<Transaction> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					this.buildTransactionEdit(row.getItem());
				}
			});
			return row;
		});

	}

}
