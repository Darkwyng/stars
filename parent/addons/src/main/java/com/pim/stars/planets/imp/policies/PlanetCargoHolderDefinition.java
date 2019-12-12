package com.pim.stars.planets.imp.policies;

import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.policies.CargoHolderDefinition;
import com.pim.stars.planets.api.Planet;

@Component
public class PlanetCargoHolderDefinition implements CargoHolderDefinition<Planet> {

	@Override
	public boolean matches(final Object object) {
		return object instanceof Planet;
	}

	@Override
	public String getCargoHolderType() {
		return Planet.class.getSimpleName();
	}

	@Override
	public String getCargoHolderId(final Planet planet) {
		return planet.getName();
	}
}