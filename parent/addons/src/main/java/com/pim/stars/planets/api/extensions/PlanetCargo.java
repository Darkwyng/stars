package com.pim.stars.planets.api.extensions;

import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.planets.api.Planet;

public class PlanetCargo implements CargoDataExtensionPolicy {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return Planet.class;
	}
}
