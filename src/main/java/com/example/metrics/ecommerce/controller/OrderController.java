package com.example.metrics.ecommerce.controller;

import com.example.metrics.ecommerce.dto.CreateOrderRequest;
import com.example.metrics.ecommerce.dto.UpdateOrderStatusRequest;
import com.example.metrics.ecommerce.order.Order;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

	static final Counter totalOrdersCounter = Counter.build()
			.name("orders_total").help("Total order.").register();
	static final Gauge activeOrdersGauge = Gauge.build()
			.name("orders_active").help("Orders not delivered yet.").register();
	static final Counter canceledOrdersCounter = Counter.build()
			.name("orders_canceled").help("Orders canceled (KPI).").register();

	private List<Order> orders = new LinkedList<>();

	@PostMapping("/api/order")
	public Order createOrder(@RequestBody CreateOrderRequest request) {
		Order createdOrder = new Order(
				request.userEmail,
				request.items.stream()
						.map(item -> new Order.Item(item.itemId, item.quantity))
						.collect(Collectors.toList())
		);
		orders.add(createdOrder);

		totalOrdersCounter.inc();
		activeOrdersGauge.inc();

		return createdOrder;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public static class OrderNotFoundException extends RuntimeException {

		public OrderNotFoundException() {}
		public OrderNotFoundException(String msg) { super(msg); }
	}

	@PatchMapping("/api/order/{orderId}")
	public void updateStatus(@PathVariable Integer orderId, @RequestBody UpdateOrderStatusRequest request) {
		Order.Status orderStatus = Order.Status.valueOf(request.status);

		Order order = orders.stream().filter(o -> o.getId() == orderId).findFirst()
				.orElseThrow(() -> new OrderNotFoundException(String.format("Order with Id %d does not exist", orderId)));

		if (order.getStatus() == orderStatus) {
			return; // for idempotency
		}

		order.setStatus(orderStatus);

		switch (order.getStatus()) {
			case CANCELED:
				canceledOrdersCounter.inc();
				activeOrdersGauge.dec();
				break;
			case DELIVERED:
				activeOrdersGauge.dec();
				break;
		}
	}
}
