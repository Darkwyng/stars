package com.pim.stars.production.imp;

import java.util.Collections;

import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.imp.cost.ProductionCostImp;

public class ProductionQueueEntry {

	private final ProductionItemType type;

	private ProductionCost investedCost;
	private int numberOfItemsToBuild;

	public ProductionQueueEntry(final ProductionItemType type) {
		super();
		this.type = type;
		investedCost = new ProductionCostImp(Collections.emptyList());
	}

	public ProductionItemType getType() {
		return type;
	}

	public ProductionCost getInvestedCost() {
		return investedCost;
	}

	public void addToInvestedCost(final ProductionCost newInvestment) {
		investedCost = investedCost.add(newInvestment);
	}

	public int getNumberOfItemsToBuild() {
		return numberOfItemsToBuild;
	}

	public void setNumberOfItemsToBuild(final int numberOfItemsToBuild) {
		this.numberOfItemsToBuild = numberOfItemsToBuild;
	}

	@Override
	public String toString() {
		return "Build " + numberOfItemsToBuild + " of " + type.getClass().getSimpleName() + " (invested " + investedCost
				+ ")";
	}
}