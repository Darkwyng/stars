package com.pim.stars.colonization.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.colonization.api.ColonistCalculator;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.resource.api.effects.ResourceCalculationPolicy;

public class ColonistResourceCalculationPolicy implements ResourceCalculationPolicy {

	@Autowired
	private ColonistCalculator colonistCalculator;

	@Override
	public int getPlanetResources(final Game game, final Planet planet, final int resources) {
		final int population = colonistCalculator.getCurrentPlanetPopulation(game, planet);
		// TODO: colonist resources: depends on their efficiency defined in the race
		return resources + population / 1000;
	}
}