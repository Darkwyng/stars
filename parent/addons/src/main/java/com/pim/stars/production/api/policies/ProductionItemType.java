package com.pim.stars.production.api.policies;

public interface ProductionItemType {

	public int getCostPerItem(); // TODO: should return ProductionCost

	public void produce(int numberOfItems);
}
