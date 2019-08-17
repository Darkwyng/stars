package com.pim.stars.planets.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public class GamePlanetCollection implements DataExtensionPolicy<Game, Collection<Planet>> {

	@Override
	public Class<Game> getEntityClass() {
		return Game.class;
	}

	@Override
	public Optional<Collection<Planet>> getDefaultValue() {
		return Optional.of(new ArrayList<Planet>());
	}
}