package com.pim.stars.race.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;

public class RaceSecondaryRacialTraitCollection implements DataExtensionPolicy<Collection<SecondaryRacialTrait>> {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return Race.class;
	}

	@Override
	public Optional<? extends Collection<SecondaryRacialTrait>> getDefaultValue() {
		return Optional.of(new ArrayList<>());
	}
}
