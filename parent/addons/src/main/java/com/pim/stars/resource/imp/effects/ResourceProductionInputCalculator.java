package com.pim.stars.resource.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;
import com.pim.stars.production.api.effects.ProductionInputCalculator;
import com.pim.stars.resource.api.ResourceCalculator;
import com.pim.stars.resource.api.ResourceProductionCostTypeProvider;

@Component
public class ResourceProductionInputCalculator implements ProductionInputCalculator {

	@Autowired
	private ResourceCalculator resourceCalculator;
	@Autowired
	private ResourceProductionCostTypeProvider resourceProductionCostTypeProvider;

	@Override
	public void calculateProductionInput(final Game game, final Planet planet, final ProductionCostBuilder builder) {
		final int resources = resourceCalculator.getResourcesForPlanet(game, planet);

		builder.add(resourceProductionCostTypeProvider.getResourceProductionCostType(), resources);
	}
}