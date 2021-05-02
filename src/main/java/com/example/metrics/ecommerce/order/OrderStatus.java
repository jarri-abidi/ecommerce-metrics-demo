package com.example.metrics.ecommerce.order;

public enum OrderStatus {
	PENDING("PENDING"),
	DISPATCHED("DISPATCHED"),
	DELIVERED("DELIVERED"),
	CANCELED("CANCELED"),
	;

	private final String text;

	OrderStatus(final String text) {
		this.text = text;
	}

	@Override public String toString() {
		return text;
	}
}
