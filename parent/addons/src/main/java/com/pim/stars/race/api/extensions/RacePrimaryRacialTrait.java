package com.pim.stars.race.api.extensions;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;
import com.pim.stars.turn.api.Race;

@Component
public class RacePrimaryRacialTrait implements DataExtensionPolicy<Race, PrimaryRacialTrait> {

	@Override
	public Class<Race> getEntityClass() {
		return Race.class;
	}

	@Override
	public Optional<? extends PrimaryRacialTrait> getDefaultValue() {
		return Optional.empty();
	}
}
