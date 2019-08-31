package com.pim.stars.production.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.ProductionCostCalculator;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.effects.ProductionInputCalculator;
import com.pim.stars.production.imp.cost.ProductionCostImp.ProductionInputBuilderImp;

@Component
public class ProductionCostCalculatorImp implements ProductionCostCalculator {

	@Autowired
	private EffectExecutor effectExecutor;

	@Override
	public ProductionCost getProductionInputForPlanet(final Game game, final Planet planet) {

		final ProductionInputBuilderImp builder = new ProductionInputBuilderImp();
		effectExecutor.executeEffect(game, ProductionInputCalculator.class, planet,
				(policy, context) -> policy.calculateProductionInput(game, planet, builder));

		return builder.build();
	} // TODO: integration test
}