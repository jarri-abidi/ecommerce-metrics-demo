package com.example.metrics.ecommerce.dto;

import java.util.List;

public class CreateOrderRequest {
	public static class Item {
		public Integer itemId;
		public Integer quantity;
	}

	public String userEmail;
	public List<Item> items;
}
