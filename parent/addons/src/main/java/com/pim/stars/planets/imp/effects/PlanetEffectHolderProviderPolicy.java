package com.pim.stars.planets.imp.effects;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.pim.stars.effect.api.policies.EffectHolderProviderPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.turn.api.Race;

@Component
public class PlanetEffectHolderProviderPolicy implements EffectHolderProviderPolicy<Planet> {

	@Autowired
	private PlanetOwnerId planetOwnerId;
	@Autowired
	private RaceProvider raceProvider;

	@Override
	public boolean matchesInitialEffectHolder(final Object effectHolder) {
		return effectHolder instanceof Planet;
	}

	@Override
	public Collection<Object> getFurtherEffectHolders(final Game game, final Planet planet) {
		final String ownerId = planetOwnerId.getValue(planet);
		if (ownerId != null) {
			final Race owner = raceProvider.getRacebyId(game, ownerId);
			Preconditions.checkNotNull(owner, "owner must not be null");
			return Collections.singleton(owner);
		} else {
			return Collections.emptySet();
		}
	}
}