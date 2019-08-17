package com.pim.stars.planets.api.extensions;

import java.util.Optional;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.GameInitializationData;

public class GameInitializationDataNumberOfPlanets implements DataExtensionPolicy<GameInitializationData, Integer> {

	@Override
	public Class<GameInitializationData> getEntityClass() {
		return GameInitializationData.class;
	}

	@Override
	public Optional<? extends Integer> getDefaultValue() {
		return Optional.of(3);
	}
}