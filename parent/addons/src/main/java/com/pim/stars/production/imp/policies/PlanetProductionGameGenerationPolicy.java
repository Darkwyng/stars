package com.pim.stars.production.imp.policies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.production.api.ProductionCostCalculator;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.extensions.PlanetProductionQueue;

@Component
public class PlanetProductionGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetProductionQueue planetProductionQueue;
	@Autowired
	private ProductionCostCalculator productionCostCalculator;

	@Override
	public void generateGame(final Game game, final GameGenerationContext generationContext) {

		final ProductionResultBuilder builder = new ProductionResultBuilder();

		// Work through queue on each planet:
		gamePlanetCollection.getValue(game).stream().map(planet -> {
			// Find queues:
			final ProductionQueue queue = planetProductionQueue.getValue(planet);
			if (queue != null && !queue.isEmpty()) {
				final ProductionCost availableResources = productionCostCalculator.getProductionInputForPlanet(game,
						planet);
				if (availableResources.isZero()) {
					return null;
				} else {
					return new ProductionExecutor(game, planet, queue, availableResources);
				}
			} else {
				return null;
			}
		}).filter(executor -> executor != null).forEach(executor -> {
			// Work on queue:
			executor.execute(builder);
		});

		// TODO: create reports for planet production
		builder.finish();
	} // TODO: integration test
}
