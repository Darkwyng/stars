package com.pim.stars.race.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.race.api.Race;

public class GameInitializationDataRaceCollection implements DataExtensionPolicy<Collection<Race>> {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return GameInitializationData.class;
	}

	@Override
	public Optional<? extends Collection<Race>> getDefaultValue() {
		return Optional.of(new ArrayList<>());
	}
}