package com.example.metrics.ecommerce.order;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.metrics.ecommerce.order.Order.Status.*;

public class Order {

	private static AtomicInteger autoIncrementId = new AtomicInteger(1);

	// allowedTransitions is a Map where each Key is an origin Status,
	// and the Value for each Key contains an array of allowed destination Statuses.
	private static Map<Status, Status[]> allowedTransitions;

	static {
		allowedTransitions = new HashMap<>();
		allowedTransitions.put(PENDING, new Status[]{DISPATCHED, CANCELED});
		allowedTransitions.put(DISPATCHED, new Status[]{DELIVERED, CANCELED});
		allowedTransitions.put(DELIVERED, new Status[]{});
		allowedTransitions.put(CANCELED, new Status[]{});
	}

	public enum Status {
		PENDING("PENDING"),
		DISPATCHED("DISPATCHED"),
		DELIVERED("DELIVERED"),
		CANCELED("CANCELED"),
		;

		private final String text;

		Status(final String text) {
			this.text = text;
		}

		@Override public String toString() {
			return text;
		}
	}

	public static class Item {
		private Integer itemId;
		private Integer quantity;

		public Item(Integer itemId, Integer quantity) {
			if (itemId < 1) { throw new InvalidArgumentException("ItemId must be greater than 0"); }
			if (quantity < 1) { throw new InvalidArgumentException("Quantity must be greater than 0"); }

			this.itemId = itemId;
			this.quantity = quantity;
		}

		public Integer getItemId() {
			return itemId;
		}
		public Integer getQuantity() {
			return quantity;
		}
	}

	private Integer id;
	private String userEmail;
	private Status status;
	private List<Item> orderedItems;

	public Integer getId() {
		return id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public Status getStatus() {
		return status;
	}

	public List<Item> getOrderedItems() {
		return orderedItems;
	}

	public Order(String userEmail, List<Item> orderedItems) {
		this.id = autoIncrementId.getAndIncrement();
		this.userEmail = userEmail;
		this.status = PENDING;
		this.orderedItems = orderedItems;
	}

	public void setStatus(Status status) {
		if (Arrays.stream(allowedTransitions.get(this.status)).noneMatch(s -> s.equals(status))) {
			throw new InvalidArgumentException(
					String.format("Order status cannot change from %s to %s", this.status.toString(), status.toString())
			);
		}
		this.status = status;
	}
}
