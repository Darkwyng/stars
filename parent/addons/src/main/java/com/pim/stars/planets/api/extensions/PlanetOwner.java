package com.pim.stars.planets.api.extensions;

import java.util.Optional;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.turn.api.Race;

public class PlanetOwner implements DataExtensionPolicy<Planet, Race> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends Race> getDefaultValue() {
		return Optional.empty();
	}
}