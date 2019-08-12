package com.pim.stars.race.api.extensions;

import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;

public class RacePrimaryRacialTrait implements DataExtensionPolicy<PrimaryRacialTrait> {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return Race.class;
	}

	@Override
	public Optional<? extends PrimaryRacialTrait> getDefaultValue() {
		return Optional.empty();
	}
}
