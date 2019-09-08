package com.pim.stars.production.api;

import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionItemType;

public interface PlanetProductionQueueManager {

	public void addToQueue(Planet planet, ProductionItemType itemType, int numberOfItemsToBuild);
}
