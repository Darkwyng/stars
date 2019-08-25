package com.pim.stars.production.imp;

import com.pim.stars.production.api.policies.ProductionItemType;

public class ProductionQueueEntry {

	private final ProductionItemType type;

	private int investedCost;
	private int numberOfItemsToBuild;

	public ProductionQueueEntry(final ProductionItemType type) {
		super();
		this.type = type;
	}

	public ProductionItemType getType() {
		return type;
	}

	public int getInvestedCost() {
		return investedCost;

	}

	public void setInvestedCost(final int investedCost) {
		this.investedCost = investedCost;
	}

	public int getNumberOfItemsToBuild() {
		return numberOfItemsToBuild;
	}

	public void setNumberOfItemsToBuild(final int numberOfItemsToBuild) {
		this.numberOfItemsToBuild = numberOfItemsToBuild;
	}
}