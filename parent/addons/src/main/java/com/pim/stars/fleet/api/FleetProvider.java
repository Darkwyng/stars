package com.pim.stars.fleet.api;

import java.util.stream.Stream;

import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface FleetProvider {

	public Stream<Fleet> getFleetsByGame(Game game);

	public Stream<Fleet> getFleetsByOwner(Game game, Race race);
}
