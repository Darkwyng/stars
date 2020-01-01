package com.pim.stars.fleet.api;

import com.pim.stars.game.api.Game;

public interface FleetSplitter {

	public FleetSplitResult splitFleet(Game game, Fleet fleet, String remainingFleetName, String newFleetName,
			String designId, int numberOfShips);

	public interface FleetSplitResult {

		public Fleet getRemainingFleet();

		public Fleet getNewFleet();
	}
}