package com.pim.stars.planets.api.extensions;

import java.util.Optional;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;

public class PlanetOwnerId implements DataExtensionPolicy<Planet, String> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends String> getDefaultValue() {
		return Optional.empty();
	}
}