package com.pim.stars.planets.api.extensions;

import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;

public class PlanetName implements DataExtensionPolicy<String> {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends String> getDefaultValue() {
		return Optional.empty();
	}
}