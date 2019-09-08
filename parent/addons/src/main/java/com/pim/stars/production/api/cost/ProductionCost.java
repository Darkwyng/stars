package com.pim.stars.production.api.cost;

import java.util.Collection;
import java.util.function.Function;

import com.pim.stars.production.api.policies.ProductionCostType;

public interface ProductionCost {

	public Collection<ProductionCostItem> getItems();

	public ProductionCost add(ProductionCost other);

	public ProductionCost subtract(ProductionCost other);

	public ProductionCost multiply(double factor, Function<Double, Integer> roundingFunction);

	public ProductionCost multiply(int factor);

	public double div(ProductionCost other);

	public boolean isZero();

	public interface ProductionCostItem {

		public ProductionCostType getType();

		public int getAmount();
	}

	public interface ProductionCostBuilder {

		public ProductionCostBuilder add(ProductionCostType type, int amount);

		public ProductionCost build();
	}
}