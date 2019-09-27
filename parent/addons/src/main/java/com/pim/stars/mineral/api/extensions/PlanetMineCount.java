package com.pim.stars.mineral.api.extensions;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;

@Component
public class PlanetMineCount implements DataExtensionPolicy<Planet, Integer> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends Integer> getDefaultValue() {
		return Optional.of(0);
	}
}