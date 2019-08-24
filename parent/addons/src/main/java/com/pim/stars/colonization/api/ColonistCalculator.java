package com.pim.stars.colonization.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public interface ColonistCalculator {

	public int getCurrentPlanetPopulation(Game game, Planet planet);

	public int getPlanetCapacity(Game game, Planet planet);

	public double getMaximumGrowthRateForPlanet(Game game, Planet planet);

	public int getExpectedColonistGainForPlanet(Game game, Planet planet);
}
