package com.pim.stars.production.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;

public interface ProductionInputCalculator extends Effect {

	public void calculateProductionInput(Game game, Planet planet, ProductionCostBuilder builder);
}
