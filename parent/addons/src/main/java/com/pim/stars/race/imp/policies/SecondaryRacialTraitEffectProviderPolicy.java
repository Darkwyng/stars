package com.pim.stars.race.imp.policies;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.policies.EffectProviderPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;

public class SecondaryRacialTraitEffectProviderPolicy implements EffectProviderPolicy<Race> {

	@Autowired
	private RaceSecondaryRacialTraitCollection raceSecondaryRacialTraitCollection;

	@Override
	public boolean matchesEffectHolder(final Object effectHolder) {
		return (effectHolder instanceof Race);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Effect> Collection<E> getEffectCollectionFromEffectHolder(final Race effectHolder,
			final Class<E> effectClass) {
		// TODO: this class should not filter.
		final Collection<E> result = new ArrayList<>();
		raceSecondaryRacialTraitCollection.getValue(effectHolder).stream().forEach(trait -> {
			trait.getEffectCollection().stream().filter(effect -> effectClass.isAssignableFrom(effect.getClass()))
					.forEach(effect -> result.add((E) effect));
		});
		return result;
	}
}
