package com.example.metrics.ecommerce.http;

import java.util.List;

public class CreateOrderRequest {
	public static class Item {
		public Integer itemId;
		public Integer quantity;
	}

	public String userEmail;
	public List<Item> items;
}
