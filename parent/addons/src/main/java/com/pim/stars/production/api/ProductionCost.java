package com.pim.stars.production.api;

import java.util.Arrays;
import java.util.Collection;

import com.pim.stars.production.api.policies.ProductionCostType;

public class ProductionCost {

	private final Collection<ProductionCostItem> items;

	public ProductionCost(final ProductionCostItem... items) {
		this.items = Arrays.asList(items);
	}

	public Collection<ProductionCostItem> getItems() {
		return items;
	}

	public class ProductionCostItem {

		private final ProductionCostType type;
		private final int amount;

		public ProductionCostItem(final ProductionCostType type, final int amount) {
			this.type = type;
			this.amount = amount;
		}

		public ProductionCostType getType() {
			return type;
		}

		public int getAmount() {
			return amount;
		}
	}
}