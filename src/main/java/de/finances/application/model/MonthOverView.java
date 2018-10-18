package de.finances.application.model;

import java.math.BigDecimal;
import java.time.Month;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthOverView {

	private int year;

	private Month month;

	private BigDecimal income;

	private BigDecimal expense;

	private BigDecimal profit;

	public MonthOverView(final int year, final Month month, final BigDecimal income, final BigDecimal expense) {
		super();
		this.year = year;
		this.month = month;
		this.income = income;
		this.expense = expense;
		this.profit = income.subtract(expense);
	}

}