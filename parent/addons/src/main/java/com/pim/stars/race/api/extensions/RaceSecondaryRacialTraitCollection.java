package com.pim.stars.race.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;
import com.pim.stars.turn.api.Race;

public class RaceSecondaryRacialTraitCollection implements DataExtensionPolicy<Race, Collection<SecondaryRacialTrait>> {

	@Override
	public Class<Race> getEntityClass() {
		return Race.class;
	}

	@Override
	public Optional<? extends Collection<SecondaryRacialTrait>> getDefaultValue() {
		return Optional.of(new ArrayList<>());
	}
}
