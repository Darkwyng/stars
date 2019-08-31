package com.pim.stars.production.api.policies;

import com.pim.stars.production.api.cost.ProductionCost;

public interface ProductionItemType {

	public ProductionCost getCostPerItem();

	public void produce(int numberOfItems);
}
