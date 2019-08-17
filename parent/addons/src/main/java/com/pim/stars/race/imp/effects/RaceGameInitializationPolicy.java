package com.pim.stars.race.imp.effects;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.api.extensions.GameRaceCollection;

public class RaceGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private GameInitializationDataRaceCollection gameInitializationDataRaceCollection;
	@Autowired
	private GameRaceCollection gameRaceCollection;

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {
		final Collection<Race> races = gameInitializationDataRaceCollection.getValue(initializationData);

		gameRaceCollection.getValue(game).addAll(races);
	}
}
