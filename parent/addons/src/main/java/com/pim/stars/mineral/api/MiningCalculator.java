package com.pim.stars.mineral.api;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public interface MiningCalculator {

	public MiningCalculatorResult calculateMining(Game game, Planet planet, double effectiveMines);

	public interface MiningCalculatorResult {

		public CargoHolder getMinedCargo();

		public void executeMining();
	}
}
