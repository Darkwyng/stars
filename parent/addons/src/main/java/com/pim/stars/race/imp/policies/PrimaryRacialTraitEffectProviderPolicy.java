package com.pim.stars.race.imp.policies;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.policies.EffectProviderPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;

public class PrimaryRacialTraitEffectProviderPolicy implements EffectProviderPolicy<Race> {

	@Autowired
	private RacePrimaryRacialTrait racePrimaryRacialTrait;

	@Override
	public boolean matchesEffectHolder(final Object effectHolder) {
		return (effectHolder instanceof Race);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Effect> Collection<E> getEffectCollectionFromEffectHolder(final Race effectHolder,
			final Class<E> effectClass) {
		return (Collection<E>) racePrimaryRacialTrait.getValue(effectHolder).getEffectCollection()
				// TODO: this class should not filter.
				.stream().filter(effect -> effectClass.isAssignableFrom(effect.getClass()))
				.collect(Collectors.toList());
	}
}
