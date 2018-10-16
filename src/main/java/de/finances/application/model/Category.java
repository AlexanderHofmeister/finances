package de.finances.application.model;

import org.apache.commons.lang3.StringUtils;

public enum Category implements LabeledEnum {

	INSURANCE,

	FOOD,

	EXT_FOOD,

	BEATUY,

	HOUSEHOLD,

	CLOTH,

	MOBILITY,

	SPARETIME,

	SPORT,

	RENT;

	@Override
	public String getLabel() {
		return StringUtils.capitalize(this.name().toLowerCase());
	}

	@Override
	public String toString() {
		return this.getLabel();
	}
}
