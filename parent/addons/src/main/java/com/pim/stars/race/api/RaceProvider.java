package com.pim.stars.race.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.turn.api.Race;

public interface RaceProvider {

	public Race getRacebyId(Game game, String raceId);
}
