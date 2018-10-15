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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class FinancesOverviewController implements Initializable {

	@FXML
	private Pane entityPane;

	@FXML
	private TableView<Transaction> transactions;

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

		this.month.setItems(
				FXCollections.observableArrayList(this.transactionService.findAllUsesdMonth(this.year.getValue())));
		this.year.setItems(FXCollections.observableArrayList(this.transactionService.findAllUsesdYears()));
		this.transactions.setItems(FXCollections.observableArrayList(
				this.transactionService.getTransactionsByMonthAndYear(this.year.getValue(), this.month.getValue())));

		this.columnDay.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getCreated().getDayOfMonth()));
		this.columnLabel.setCellValueFactory(new PropertyValueFactory<Transaction, String>("label"));
		this.columnCategory.setCellValueFactory(new PropertyValueFactory<Transaction, Category>("category"));
		this.columnValue.setCellValueFactory(new PropertyValueFactory<Transaction, BigDecimal>("value"));

	}

}
