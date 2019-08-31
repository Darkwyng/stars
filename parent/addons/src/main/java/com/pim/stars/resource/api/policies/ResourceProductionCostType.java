package com.pim.stars.resource.api.policies;

import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionCostType;

@Component
public class ResourceProductionCostType implements ProductionCostType {

	@Override
	public String getId() {
		return "R";
	}

	@Override
	public void deduct(final Game game, final Planet planet, final int amount) {
		// When research is implemented, we will want to know how many resources are left over after production.
		// For now: nothing to do
	}
}