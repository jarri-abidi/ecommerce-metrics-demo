package com.example.metrics.ecommerce.controller;

import com.example.metrics.ecommerce.order.Order;
import com.example.metrics.ecommerce.order.OrderNotFoundException;
import com.example.metrics.ecommerce.order.OrderStatus;
import com.example.metrics.ecommerce.order.OrderedItem;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

class CreateOrderRequest {
	public String userEmail;
	public List<OrderedItem> items;
}

class UpdateOrderStatusRequest {
	public OrderStatus status;
}

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
		Order createdOrder = new Order((int) totalOrdersCounter.get()+1, request.userEmail, request.items);
		orders.add(createdOrder);

		totalOrdersCounter.inc();
		activeOrdersGauge.inc();

		return createdOrder;
	}

	@PatchMapping("/api/order/{orderId}")
	public void updateStatus(@PathVariable Integer orderId, @RequestBody UpdateOrderStatusRequest request) {
		Order order = orders.stream().filter(o -> o.getId() == orderId).findFirst()
				.orElseThrow(() -> new OrderNotFoundException(String.format("Order with Id %d does not exist", orderId)));

		if (order.getStatus() == request.status) {
			return; // for idempotency
		}

		order.setStatus(request.status);

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
