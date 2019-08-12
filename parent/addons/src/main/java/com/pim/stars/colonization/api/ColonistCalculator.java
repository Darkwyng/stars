package com.pim.stars.colonization.api;

import com.pim.stars.planets.api.Planet;

public interface ColonistCalculator {

	public int getCurrentPlanetPopulation(Planet planet);

	public int getPlanetCapacity(Planet planet);

	public double getMaximumGrowthRateForPlanet(Planet planet);

	public int getExpectedColonistGainForPlanet(Planet planet);
}
