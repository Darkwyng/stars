package com.pim.stars.race.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.race.api.RaceInitializationData;

@Component
public class GameInitializationDataRaceCollection
		implements DataExtensionPolicy<GameInitializationData, Collection<RaceInitializationData>> {

	@Override
	public Class<GameInitializationData> getEntityClass() {
		return GameInitializationData.class;
	}

	@Override
	public Optional<? extends Collection<RaceInitializationData>> getDefaultValue() {
		return Optional.of(new ArrayList<>());
	}
}