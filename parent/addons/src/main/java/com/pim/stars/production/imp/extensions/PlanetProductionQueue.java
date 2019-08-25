package com.pim.stars.production.imp.extensions;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.imp.ProductionQueue;

@Component
public class PlanetProductionQueue implements DataExtensionPolicy<Planet, ProductionQueue> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends ProductionQueue> getDefaultValue() {
		return Optional.empty();
	}
}