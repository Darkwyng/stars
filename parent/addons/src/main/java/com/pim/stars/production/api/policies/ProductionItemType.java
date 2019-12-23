package com.pim.stars.production.api.policies;

import java.util.Collection;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;

public interface ProductionItemType {

	default public String getId() {
		return getClass().getSimpleName();
	}

	public ProductionCost getCostPerItem(Game game, Planet planet, ProductionCostBuilder builder);

	public void produce(Game game, Planet planet, int numberOfItems);

	public interface ProductionItemTypeFactory {

		public Collection<ProductionItemType> createProductionItemTypes();
	}
}
