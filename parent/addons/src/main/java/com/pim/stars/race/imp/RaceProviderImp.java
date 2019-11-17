package com.pim.stars.race.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.turn.api.Race;

@Component
public class RaceProviderImp implements RaceProvider {

	@Autowired
	private GameRaceCollection gameRaceCollection;

	@Override
	public Race getRacebyId(final Game game, final String raceId) {
		return gameRaceCollection.getValue(game).stream().filter(race -> race.getId().equals(raceId)).findAny()
				.orElseThrow(() -> new IllegalArgumentException("No race found with ID " + raceId));
	}
}