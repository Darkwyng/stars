package com.pim.stars.race.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;

@Component
public class RaceSecondaryRacialTraitCollection
		implements DataExtensionPolicy<RaceInitializationData, Collection<SecondaryRacialTrait>> {

	@Override
	public Class<RaceInitializationData> getEntityClass() {
		return RaceInitializationData.class;
	}

	@Override
	public Optional<? extends Collection<SecondaryRacialTrait>> getDefaultValue() {
		return Optional.of(new ArrayList<>());
	}
}
