package com.pim.stars.mineral.api.extensions;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations.MineralConcentrations;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.planets.api.Planet;

@Component
public class PlanetMineralConcentrations implements DataExtensionPolicy<Planet, MineralConcentrations> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends MineralConcentrations> getDefaultValue() {
		return Optional.empty();
	}

	public interface MineralConcentrations extends Iterable<MineralConcentration> {

	}

	public interface MineralConcentration {

		public MineralType getType();

		public double getAmount();
	}
}