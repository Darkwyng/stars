package com.pim.stars.planets.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public class GamePlanetCollection implements DataExtensionPolicy<Collection<Planet>> {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return Game.class;
	}

	@Override
	public Optional<Collection<Planet>> getDefaultValue() {
		return Optional.of(new ArrayList<Planet>());
	}
}