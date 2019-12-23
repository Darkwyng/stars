package com.pim.stars.production.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.colonization.api.effects.PlanetColonizationPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.imp.persistence.ProductionPersistenceInterface;
import com.pim.stars.race.api.Race;

@Component
public class PlanetProductionColonizationPolicy implements PlanetColonizationPolicy {

	@Autowired
	private ProductionPersistenceInterface productionPersistenceInterface;

	@Override
	public void colonizePlanet(final Game game, final Planet planet, final Race race) {
		productionPersistenceInterface.createNewQueue(game, planet);
	}
}