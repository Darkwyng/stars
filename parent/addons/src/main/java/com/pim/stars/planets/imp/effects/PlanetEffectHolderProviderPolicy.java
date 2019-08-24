package com.pim.stars.planets.imp.effects;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.effect.api.policies.EffectHolderProviderPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.turn.api.Race;

public class PlanetEffectHolderProviderPolicy implements EffectHolderProviderPolicy<Planet> {

	@Autowired
	private PlanetOwnerId planetOwnerId;
	@Autowired
	private GameRaceCollection gameRaceCollection;
	@Autowired
	private RaceId raceId;

	@Override
	public boolean matchesInitialEffectHolder(final Object effectHolder) {
		return effectHolder instanceof Planet;
	}

	@Override
	public Collection<Object> getFurtherEffectHolders(final Game game, final Planet planet) {
		final String ownerId = planetOwnerId.getValue(planet);
		if (ownerId != null) {
			final Race owner = gameRaceCollection.getValue(game).stream()
					.filter(race -> raceId.getValue(race).equals(ownerId)).findAny().get();
			return Collections.singleton(owner);
		} else {
			return Collections.emptySet();
		}
	}
}