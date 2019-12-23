package com.pim.stars.production.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionItemType;

public interface PlanetProductionQueueManager {

	public void addToQueue(Game game, Planet planet, ProductionItemType itemType, int numberOfItemsToBuild);
}
