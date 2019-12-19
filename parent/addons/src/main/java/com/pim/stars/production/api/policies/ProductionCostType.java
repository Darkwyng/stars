package com.pim.stars.production.api.policies;

import java.util.Collection;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public interface ProductionCostType {

	public String getId();

	public void deduct(Game game, Planet planet, int amount);

	public interface ProductionCostTypeFactory {

		public Collection<ProductionCostType> createProductionCostTypes();
	}

}
