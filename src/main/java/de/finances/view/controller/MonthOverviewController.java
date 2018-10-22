package de.finances.view.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import de.finances.application.model.Category;
import de.finances.application.model.MonthOverView;
import de.finances.application.model.Transaction;
import de.finances.application.model.TransactionType;
import de.finances.application.service.TransactionService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Pair;

public class MonthOverviewController implements Initializable {

	private final TransactionService transactionService = new TransactionService();

	@FXML
	public TableView<Pair<Category, BigDecimal>> expensesByCategoryTable;

	@FXML
	public TableColumn<Pair<Category, BigDecimal>, String> categoryColumn;

	@FXML
	public TableColumn<Pair<Category, BigDecimal>, BigDecimal> sumColumn;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.categoryColumn
				.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getKey().getLabel()));
		this.sumColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getValue()));
		this.expensesByCategoryTable.getItems().add(new Pair<>(Category.CLOTH, new BigDecimal(5)));
	}

	public void setMonthOverview(final MonthOverView monthOverView) {
		final List<Pair<Category, BigDecimal>> expensesByCategory = this.transactionService
				.getTransactionsByMonthAndYear(monthOverView.getYear(), monthOverView.getMonth()).stream()
				.filter(t -> t.getType() == TransactionType.EXPENSE && t.getCategory() != null)
				.collect(Collectors.groupingBy(Transaction::getCategory,
						Collectors.reducing(BigDecimal.ZERO, Transaction::getValue, BigDecimal::add)))
				.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
		this.expensesByCategoryTable.setItems(FXCollections.observableArrayList(expensesByCategory));

	}

}
