package com.pim.stars.race.imp.policies;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.policies.EffectCollectionProviderPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.imp.persistence.RaceEntity;
import com.pim.stars.race.imp.persistence.RaceRepository;
import com.pim.stars.turn.api.Race;

@Component
public class PrimaryRacialTraitEffectProviderPolicy implements EffectCollectionProviderPolicy<Race> {

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
		final RaceEntity entity = raceRepository.findByGameIdAndRaceId(game.getId(), effectHolder.getId());
		final String id = entity.getPrimaryRacialTraitId();

		return raceTraitProvider.getPrimaryRacialTraitById(id)
				.orElseThrow(() -> new IllegalArgumentException("No primary trait found for ID " + id))
				.getEffectCollection();
	}
}
