package com.pim.stars.planets.imp.effects;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.policies.EffectHolderProviderPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.turn.api.Race;

@Component
public class PlanetEffectHolderProviderPolicy implements EffectHolderProviderPolicy<Planet> {

	@Autowired
	private RaceProvider raceProvider;

	@Override
	public boolean matchesInitialEffectHolder(final Object effectHolder) {
		return effectHolder instanceof Planet;
	}

	@Override
	public Collection<Object> getFurtherEffectHolders(final Game game, final Planet planet) {
		final Optional<String> ownerId = planet.getOwnerId();
		if (ownerId.isPresent()) {
			final Race owner = raceProvider.getRaceById(game, ownerId.get());
			return Collections.singleton(owner);
		} else {
			return Collections.emptySet();
		}
	}
}