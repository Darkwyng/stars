package com.pim.stars.planets.api.extensions;

import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.planets.api.Planet;

public class PlanetCargo implements CargoDataExtensionPolicy<Planet> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}
}