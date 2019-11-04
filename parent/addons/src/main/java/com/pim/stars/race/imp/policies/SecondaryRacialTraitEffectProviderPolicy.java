package com.pim.stars.race.imp.policies;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.policies.EffectCollectionProviderPolicy;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;
import com.pim.stars.turn.api.Race;

@Component
public class SecondaryRacialTraitEffectProviderPolicy implements EffectCollectionProviderPolicy<Race> {

	@Autowired
	private RaceSecondaryRacialTraitCollection raceSecondaryRacialTraitCollection;

	@Override
	public boolean matchesEffectHolder(final Object effectHolder) {
		return (effectHolder instanceof Race);
	}

	@Override
	public Collection<Effect> getEffectCollectionFromEffectHolder(final Race effectHolder) {
		final Collection<Effect> result = new ArrayList<>();
		raceSecondaryRacialTraitCollection.getValue(effectHolder).stream().forEach(trait -> {
			trait.getEffectCollection().stream().forEach(effect -> result.add(effect));
		});
		return result;
	}
}
