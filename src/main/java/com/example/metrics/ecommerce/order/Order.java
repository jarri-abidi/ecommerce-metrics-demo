package com.example.metrics.ecommerce.order;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.metrics.ecommerce.order.OrderStatus.*;

public class Order {

	private static AtomicInteger autoIncrementId = new AtomicInteger(1);

	// allowedTransitions is a Map where each Key is an origin Status,
	// and the Value for each Key contains an array of allowed destination Statuses.
	private static Map<OrderStatus, OrderStatus[]> allowedTransitions;

	static {
		allowedTransitions = new HashMap<>();
		allowedTransitions.put(PENDING, new OrderStatus[]{DISPATCHED, CANCELED});
		allowedTransitions.put(DISPATCHED, new OrderStatus[]{DELIVERED, CANCELED});
		allowedTransitions.put(DELIVERED, new OrderStatus[]{});
		allowedTransitions.put(CANCELED, new OrderStatus[]{});
	}

	private Integer id;
	private String userEmail;
	private OrderStatus status;
	private List<OrderedItem> orderedItems;

	public Integer getId() {
		return id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public Order(String userEmail, List<OrderedItem> orderedItems) {
		this.id = autoIncrementId.getAndIncrement();
		this.userEmail = userEmail;
		this.status = PENDING;
		this.orderedItems = orderedItems;
	}

	public void setStatus(OrderStatus newStatus) {
		if (Arrays.stream(allowedTransitions.get(this.status)).noneMatch(s -> s.equals(newStatus))) {
			throw new InvalidArgumentException(
					String.format("Order status cannot change from %s to %s", this.status.toString(), newStatus.toString())
			);
		}
		this.status = newStatus;
	}
}
