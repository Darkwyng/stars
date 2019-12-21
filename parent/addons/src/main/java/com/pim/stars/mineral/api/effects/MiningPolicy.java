package com.pim.stars.mineral.api.effects;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public interface MiningPolicy extends Effect {

	public MiningPolicyResult calculateMining(Game game, Planet planet);

	public interface MiningPolicyResult {

		public CargoHolder getMinedCargo();

		public void executeMining();
	}
}
