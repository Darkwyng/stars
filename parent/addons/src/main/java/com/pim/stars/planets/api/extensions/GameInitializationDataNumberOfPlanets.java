package com.pim.stars.planets.api.extensions;

import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.GameInitializationData;

public class GameInitializationDataNumberOfPlanets implements DataExtensionPolicy<Integer> {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return GameInitializationData.class;
	}

	@Override
	public Optional<? extends Integer> getDefaultValue() {
		return Optional.of(3);
	}
}