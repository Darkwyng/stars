package com.pim.stars.production.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost;

public interface ProductionCostCalculator {

	public ProductionCost getProductionInputForPlanet(Game game, Planet planet);
}
