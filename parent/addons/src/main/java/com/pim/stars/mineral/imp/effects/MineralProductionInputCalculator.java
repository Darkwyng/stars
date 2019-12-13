package com.pim.stars.mineral.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;
import com.pim.stars.production.api.effects.ProductionInputCalculator;

@Component
public class MineralProductionInputCalculator implements ProductionInputCalculator {

	@Autowired
	private CargoProcessor cargoProcessor;

	@Override
	public void calculateProductionInput(final Game game, final Planet planet, final ProductionCostBuilder builder) {

		cargoProcessor.createCargoHolder(game, planet).getItems().stream()
				.filter(item -> item.getType() instanceof MineralType).forEach(item -> {
					builder.add((MineralType) item.getType(), item.getQuantity());
				});
	}
}