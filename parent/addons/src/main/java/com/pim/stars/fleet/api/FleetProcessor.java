package com.pim.stars.fleet.api;

import java.util.Collection;

import com.pim.stars.game.api.Game;

public interface FleetProcessor {

	public Fleet createFleet(Game game, String raceId, String fleetName, String designId, int numberOfShips);

	public Fleet mergeFleets(Game game, String newFleetName, Collection<Fleet> fleets);

	public void deleteFleets(Game game, Collection<Fleet> fleets);
}
