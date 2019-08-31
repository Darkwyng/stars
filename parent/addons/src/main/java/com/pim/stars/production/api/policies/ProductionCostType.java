package com.pim.stars.production.api.policies;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public interface ProductionCostType {

	public String getId();

	public void deduct(Game game, Planet planet, int amount);
}
