package com.pim.stars.race.api;

import java.util.stream.Stream;

import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface RaceProvider {

	public Race getRaceById(Game game, String raceId);

	public Stream<Race> getRacesByGame(Game game);
}
