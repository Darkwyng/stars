package com.pim.stars.planets.api.extensions;

import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.planets.api.Planet;

@Component
public class PlanetCargo implements CargoDataExtensionPolicy<Planet> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}
}