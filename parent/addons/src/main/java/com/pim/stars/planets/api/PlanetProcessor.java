package com.pim.stars.planets.api;

import com.pim.stars.game.api.Game;

public interface PlanetProcessor {

	public Planet setPlanetOwnerId(Game game, Planet planet, String raceId);
}
