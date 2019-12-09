package com.pim.stars.planets.api;

import java.util.stream.Stream;

import com.pim.stars.game.api.Game;

public interface PlanetProvider {

	public Stream<Planet> getPlanetsByGame(Game game);
}
