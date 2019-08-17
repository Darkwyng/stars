package com.pim.stars.race.api.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.turn.api.Race;

public class GameRaceCollection implements DataExtensionPolicy<Game, Collection<Race>> {

	@Override
	public Class<Game> getEntityClass() {
		return Game.class;
	}

	@Override
	public Optional<Collection<Race>> getDefaultValue() {
		return Optional.of(new ArrayList<Race>());
	}
}