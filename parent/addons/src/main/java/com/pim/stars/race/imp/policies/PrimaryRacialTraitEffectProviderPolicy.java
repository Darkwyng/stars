package com.pim.stars.race.imp.policies;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.policies.EffectCollectionProviderPolicy;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;
import com.pim.stars.turn.api.Race;

public class PrimaryRacialTraitEffectProviderPolicy implements EffectCollectionProviderPolicy<Race> {

	@Autowired
	private RacePrimaryRacialTrait racePrimaryRacialTrait;

	@Override
	public boolean matchesEffectHolder(final Object effectHolder) {
		return (effectHolder instanceof Race);
	}

	@Override
	public Collection<Effect> getEffectCollectionFromEffectHolder(final Race effectHolder) {
		return racePrimaryRacialTrait.getValue(effectHolder).getEffectCollection();
	}
}
