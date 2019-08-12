package com.pim.stars.planets.api.extensions;

import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.race.api.Race;

public class PlanetOwner implements DataExtensionPolicy<Race> {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends Race> getDefaultValue() {
		return Optional.empty();
	}
}