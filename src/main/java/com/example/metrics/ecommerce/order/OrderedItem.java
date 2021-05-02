package com.example.metrics.ecommerce.order;

public class OrderedItem {

	private Integer itemId;
	private Integer quantity;

	public OrderedItem(Integer itemId, Integer quantity) {
		if (itemId < 1) {
			throw new InvalidArgumentException("ItemId must be greater than 0");
		}

		if (quantity < 1) {
			throw new InvalidArgumentException("Quantity must be greater than 0");
		}

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
