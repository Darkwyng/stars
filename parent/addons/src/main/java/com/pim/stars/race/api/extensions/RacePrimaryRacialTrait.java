package com.pim.stars.race.api.extensions;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;

@Component
public class RacePrimaryRacialTrait implements DataExtensionPolicy<RaceInitializationData, PrimaryRacialTrait> {

	@Override
	public Class<RaceInitializationData> getEntityClass() {
		return RaceInitializationData.class;
	}

	@Override
	public Optional<? extends PrimaryRacialTrait> getDefaultValue() {
		return Optional.empty();
	}
}
