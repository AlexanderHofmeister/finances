package de.finances.application.model;

import org.apache.commons.lang3.StringUtils;

public enum TransactionType {

	INCOME,

	EXPENSE;

	@Override
	public String toString() {
		return StringUtils.capitalize(this.name().toLowerCase());
	}
}
