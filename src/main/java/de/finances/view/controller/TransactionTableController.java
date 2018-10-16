package de.finances.view.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

import de.finances.application.model.Category;
import de.finances.application.model.Transaction;
import de.finances.application.service.TransactionService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

public class TransactionTableController implements Initializable {

	@FXML
	@Getter
	private TableView<Transaction> transactionTable;

	@FXML
	private TableColumn<Transaction, Integer> columnDay;

	@FXML
	private TableColumn<Transaction, String> columnLabel;

	@FXML
	private TableColumn<Transaction, Category> columnCategory;

	@FXML
	private TableColumn<Transaction, BigDecimal> columnValue;

	@FXML
	private ComboBox<Month> month;

	@FXML
	private ComboBox<Integer> year;

	private final TransactionService transactionService = new TransactionService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		this.month.setValue(LocalDate.now().getMonth());
		this.year.setValue(LocalDate.now().getYear());

		this.month.valueProperty().addListener((ChangeListener<Month>) (observable, oldValue,
				newValue) -> TransactionTableController.this.loadTransactions());

		this.month.setItems(
				FXCollections.observableArrayList(this.transactionService.findAllUsesdMonth(this.year.getValue())));
		this.year.setItems(FXCollections.observableArrayList(this.transactionService.findAllUsesdYears()));
		this.loadTransactions();

		this.columnDay.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getCreated().getDayOfMonth()));
		this.columnLabel.setCellValueFactory(new PropertyValueFactory<Transaction, String>("label"));
		this.columnCategory.setCellValueFactory(new PropertyValueFactory<Transaction, Category>("category"));
		this.columnValue.setCellValueFactory(new PropertyValueFactory<Transaction, BigDecimal>("value"));

	}

	private void loadTransactions() {
		this.transactionTable.getItems().setAll(FXCollections.observableArrayList(
				this.transactionService.getTransactionsByMonthAndYear(this.year.getValue(), this.month.getValue())));
	}

}
