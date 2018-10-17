package de.finances.view.controller;

import java.math.BigDecimal;

import de.finances.application.model.Category;
import de.finances.application.model.Transaction;
import de.finances.application.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

public class TransactionEditController {

	@FXML
	private TextField label;

	@FXML
	private TextField value;

	@FXML
	private DatePicker date;

	@FXML
	private ComboBox<Category> category;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	@FXML
	private Label heading;

	private final TransactionService transactionService = new TransactionService();

	private Transaction transaction;

	public void saveTransaction() {
		this.transaction.setCategory(this.category.getValue());
		this.transaction.setLabel(this.label.getText());
		this.transaction.setCreated(this.date.getValue());
		this.transaction.setValue(new BigDecimal(this.value.getText()));
		this.transactionService.update(this.transaction);
	}

	public void setTransaction(final Transaction transaction) {
		this.transaction = transaction;
		this.category.setItems(FXCollections.observableArrayList(Category.values()));

		this.heading.setText(transaction.isNew() ? "Create " + transaction.getType()
				: "Edit " + transaction.getType() + " " + transaction.getLabel());
		this.label.setText(transaction.getLabel());
		this.value.setText(transaction.getValue().toString());
		this.date.setValue(transaction.getCreated());
		this.category.setValue(transaction.getCategory());

	}

}
