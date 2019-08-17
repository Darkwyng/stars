package com.pim.stars.race.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.turn.api.Race;

public class GameInitializationDataRaceCollection
		implements DataExtensionPolicy<GameInitializationData, Collection<Race>> {

	@Override
	public Class<GameInitializationData> getEntityClass() {
		return GameInitializationData.class;
	}

	@Override
	public Optional<? extends Collection<Race>> getDefaultValue() {
		return Optional.of(new ArrayList<>());
	}
}