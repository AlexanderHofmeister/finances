package de.finances.application.service;

import java.util.HashMap;
import java.util.Map;

public class QueryParameter {

	public static QueryParameter with(final String name, final Object value) {
		return new QueryParameter(name, value);
	}

	private Map<String, Object> parameters = new HashMap<>();

	private QueryParameter(final String name, final Object value) {
		this.parameters.put(name, value);
	}

	public QueryParameter and(final String name, final Object value) {
		this.parameters.put(name, value);
		return this;
	}

	public Map<String, Object> parameters() {
		return this.parameters;
	}

}