package com.pim.stars.resource.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public interface ResourceCalculator {

	public int getResourcesForPlanet(final Game game, final Planet planet);
}
