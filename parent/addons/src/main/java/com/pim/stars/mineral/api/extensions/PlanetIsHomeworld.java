package com.pim.stars.mineral.api.extensions;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;

@Component
public class PlanetIsHomeworld implements DataExtensionPolicy<Planet, Boolean> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends Boolean> getDefaultValue() {
		return Optional.of(false);
	}
}