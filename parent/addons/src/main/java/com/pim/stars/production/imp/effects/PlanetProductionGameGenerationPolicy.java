package com.pim.stars.production.imp.effects;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.api.ProductionCostCalculator;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.persistence.ProductionPersistenceInterface;

@Component
public class PlanetProductionGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private ProductionPersistenceInterface productionPersistenceInterface;
	@Autowired
	private ProductionCostCalculator productionCostCalculator;

	@Override
	public int getSequence() {
		return 1200;
	}

	@Override
	public void generateGame(final GameGenerationContext generationContext) {

		final ProductionResultBuilder builder = new ProductionResultBuilder();

		// Work through queue on each planet:
		final Game game = generationContext.getCurrentYear();

		final List<ProductionQueue> queuesAfterProduction = productionPersistenceInterface.getQueues(game)
				.map(queue -> mapQueueToExecutor(game, queue)).filter(executor -> executor != null)
				.map(executor -> executor.execute(builder)).collect(Collectors.toList());

		productionPersistenceInterface.persistModifiedQueues(game, queuesAfterProduction);

		// TODO: report for production
		builder.finish();
	}

	protected ProductionExecutor mapQueueToExecutor(final Game game, final ProductionQueue queue) {
		if (queue.isEmpty()) {
			return null;
		} else {
			final Planet planet = planetProvider.getPlanet(game, queue.getPlanetName());
			final ProductionCost availableResources = productionCostCalculator.getProductionInputForPlanet(game,
					planet);
			if (availableResources.isZero()) {
				return null;
			} else {
				return new ProductionExecutor(game, planet, queue, availableResources);
			}
		}
	}
}
