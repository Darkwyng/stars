package com.pim.stars.production.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.PlanetProductionQueueManager;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.imp.persistence.ProductionPersistenceInterface;

@Component
public class PlanetProductionQueueManagerImp implements PlanetProductionQueueManager {

	@Autowired
	private ProductionPersistenceInterface productionPersistenceInterface;

	@Override
	public void addToQueue(final Game game, final Planet planet, final ProductionItemType itemType,
			final int numberOfItemsToBuild) {
		productionPersistenceInterface.addToQueue(game, planet, itemType, numberOfItemsToBuild);
	}
}
