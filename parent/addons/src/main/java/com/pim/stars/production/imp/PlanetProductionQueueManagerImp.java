package com.pim.stars.production.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.PlanetProductionQueueManager;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.imp.extensions.PlanetProductionQueue;

@Component
public class PlanetProductionQueueManagerImp implements PlanetProductionQueueManager {

	@Autowired
	private PlanetProductionQueue planetProductionQueue;

	@Override
	public void addToQueue(final Planet planet, final ProductionItemType itemType, final int numberOfItemsToBuild) {
		final ProductionQueueEntry entry = new ProductionQueueEntry(itemType);
		entry.setNumberOfItemsToBuild(numberOfItemsToBuild);
		planetProductionQueue.getValue(planet).addEntry(entry);
	}
}
