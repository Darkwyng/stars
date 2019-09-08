package com.pim.stars.production.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionItemType;

public interface ProductionAvailabilityCalculator {

	public boolean isProductionItemTypeAvailable(Game game, Planet planet, ProductionItemType itemType);
}
