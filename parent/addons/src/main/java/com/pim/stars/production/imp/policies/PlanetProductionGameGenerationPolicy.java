package com.pim.stars.production.imp.policies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.ProductionQueueEntry;
import com.pim.stars.production.imp.extensions.PlanetProductionQueue;
import com.pim.stars.production.imp.policies.ProductionResultBuilder.PlanetProductionResultBuilder;
import com.pim.stars.resource.api.ResourceCalculator;

@Component
public class PlanetProductionGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetProductionQueue planetProductionQueue;
	@Autowired
	private ResourceCalculator resourceCalculator;

	@Override
	public void generateGame(final Game game, final GameGenerationContext generationContext) {

		final ProductionResultBuilder builder = new ProductionResultBuilder();

		// Work through queue on each planet:
		gamePlanetCollection.getValue(game).stream().map(planet -> {
			// Find queues:
			final ProductionQueue queue = planetProductionQueue.getValue(planet);
			if (queue != null && !queue.isEmpty()) {
				final int resources = resourceCalculator.getResourcesForPlanet(game, planet);
				return new ProductionExecutor(planet, queue, resources);
			} else {
				return null;
			}
		}).filter(executor -> executor != null).forEach(executor -> {
			// Work on queue:
			executor.execute(builder);
		});

		// TODO: create reports for planet production
		builder.finish();
	}

	private static class ProductionExecutor {

		private final Planet planet;
		private final ProductionQueue queue;
		private int remainingResources;

		public ProductionExecutor(final Planet planet, final ProductionQueue queue, final int initialResources) {
			this.planet = planet;
			this.queue = queue;
			this.remainingResources = initialResources;
		}

		public void execute(final ProductionResultBuilder builder) {

			final PlanetProductionResultBuilder planetResultBuilder = builder.startPlanet(planet);

			for (final ProductionQueueEntry entry : queue) {
				final ProductionItemType type = entry.getType();
				final int costPerItem = type.getCostPerItem();
				final int previousInvestment = entry.getInvestedCost();
				final int numberToBuild = entry.getNumberOfItemsToBuild();

				final int availableResources = previousInvestment + remainingResources;
				final int possibleNumberOfItemsToBuild = availableResources / costPerItem;

				if (possibleNumberOfItemsToBuild == 0) {
					// No item can be built:
					// Just invest:
					entry.setInvestedCost(availableResources);
					remainingResources = 0;

				} else if (possibleNumberOfItemsToBuild < numberToBuild) {
					// Some items can be built, but not all:
					// Build them:
					produce(planetResultBuilder, type, possibleNumberOfItemsToBuild);
					// Pay for them:
					final int totalCost = possibleNumberOfItemsToBuild * costPerItem;
					remainingResources = remainingResources - totalCost;
					// Invest the rest:
					entry.setInvestedCost(remainingResources);
					remainingResources = 0;

				} else if (possibleNumberOfItemsToBuild >= numberToBuild) {
					// All items can be built:
					// Build them:
					produce(planetResultBuilder, type, numberToBuild);
					// Pay for them:
					final int totalCost = numberToBuild * costPerItem;
					remainingResources = remainingResources - totalCost;
					// (nothing to invest, continue with next item in queue)
				}

				if (remainingResources == 0) {
					// No more items to work on:
					break;
				}
			}
			planetResultBuilder.finishPlanet(remainingResources);
		}

		private void produce(final PlanetProductionResultBuilder planetResultBuilder, final ProductionItemType type,
				final int numberOfItems) {
			type.produce(numberOfItems);
			planetResultBuilder.produce(type, numberOfItems);
		}
	}
}
