package com.pim.stars.race.imp.policies;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.policies.EffectCollectionProviderPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.imp.persistence.RaceRepository;
import com.pim.stars.turn.api.Race;

@Component
public class SecondaryRacialTraitEffectProviderPolicy implements EffectCollectionProviderPolicy<Race> {

	@Autowired
	private RaceTraitProvider raceTraitProvider;
	@Autowired
	private RaceRepository raceRepository;

	@Override
	public boolean matchesEffectHolder(final Object effectHolder) {
		return (effectHolder instanceof Race);
	}

	@Override
	public Collection<Effect> getEffectCollectionFromEffectHolder(final Game game, final Race effectHolder) {
		return raceRepository.findByGameIdAndRaceId(game.getId(), effectHolder.getId()).getSecondaryRacialTraitIds()
				.stream()
				.map(id -> raceTraitProvider.getSecondaryRacialTraitById(id)
						.orElseThrow(() -> new IllegalArgumentException("No secondary trait found for ID " + id))
						.getEffectCollection())
				.flatMap(c -> c.stream()).collect(Collectors.toList());
	}
}
