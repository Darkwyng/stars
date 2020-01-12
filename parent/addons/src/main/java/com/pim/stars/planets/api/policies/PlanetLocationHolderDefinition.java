package com.pim.stars.planets.api.policies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.policies.LocationHolderDefinition;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;

@Component
public class PlanetLocationHolderDefinition implements LocationHolderDefinition<Planet> {

	@Autowired
	private PlanetProvider planetProvider;

	@Override
	public boolean matches(final Object object) {
		return object instanceof Planet;
	}

	@Override
	public String getLocationHolderType() {
		return Planet.class.getSimpleName();
	}

	@Override
	public String getLocationHolderId(final Planet planet) {
		return planet.getName();
	}

	@Override
	public Planet toObject(Game game, final String id) {
		return planetProvider.getPlanet(game, id);
	}

}
