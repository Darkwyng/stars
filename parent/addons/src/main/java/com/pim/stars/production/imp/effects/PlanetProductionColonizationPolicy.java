package com.pim.stars.production.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.colonization.api.effects.PlanetColonizationPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.extensions.PlanetProductionQueue;
import com.pim.stars.turn.api.Race;

@Component
public class PlanetProductionColonizationPolicy implements PlanetColonizationPolicy {

	@Autowired
	private PlanetProductionQueue planetProductionQueue;

	@Override
	public void colonizePlanet(final Game game, final Planet planet, final Race race) {
		planetProductionQueue.setValue(planet, new ProductionQueue());
	}
}