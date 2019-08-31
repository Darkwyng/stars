package com.pim.stars.production.imp.policies;

import java.util.function.Function;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.ProductionQueueEntry;
import com.pim.stars.production.imp.policies.ProductionResultBuilder.PlanetProductionResultBuilder;

public class ProductionExecutor {

	private final Game game;
	private final Planet planet;
	private final ProductionQueue queue;
	private ProductionCost remainingResources;

	public ProductionExecutor(final Game game, final Planet planet, final ProductionQueue queue,
			final ProductionCost initialResources) {
		this.game = game;
		this.planet = planet;
		this.queue = queue;
		this.remainingResources = initialResources;
	}

	public void execute(final ProductionResultBuilder builder) {

		final PlanetProductionResultBuilder planetResultBuilder = builder.startPlanet(planet);

		for (final ProductionQueueEntry entry : queue) {
			final ProductionCost costPerItem = entry.getType().getCostPerItem();
			final int numberToBuild = entry.getNumberOfItemsToBuild();

			final int numberToBuildNow = getNumberToBuildNow(entry, costPerItem, numberToBuild);

			// If some can be built...
			if (numberToBuildNow > 0) {
				final ProductionCost totalAdditionalCost = costPerItem.multiply(numberToBuildNow)
						.subtract(entry.getInvestedCost());
				// Pay for them:
				pay(totalAdditionalCost);
				// Build them:
				produce(planetResultBuilder, entry, numberToBuildNow);
			}

			// If more need building...
			if (numberToBuildNow < numberToBuild) {
				// Invest the rest:
				invest(entry, costPerItem);
			}
		}

		// Remove finished items:
		queue.cleanUp();

		planetResultBuilder.finishPlanet(remainingResources);
	}

	private int getNumberToBuildNow(final ProductionQueueEntry entry, final ProductionCost costPerItem,
			final int numberToBuild) {
		final ProductionCost previousInvestment = entry.getInvestedCost();
		final ProductionCost availableResources = previousInvestment.add(remainingResources);
		final int possibleNumberOfItemsToBuild = (int) Math.round(Math.floor(availableResources.div(costPerItem)));

		return Math.min(numberToBuild, possibleNumberOfItemsToBuild);
	}

	private void invest(final ProductionQueueEntry entry, final ProductionCost costPerItem) {
		// Determine which fraction of the cost to invest:
		final double fraction = remainingResources.div(costPerItem);
		final ProductionCost actualInvestment = costPerItem.multiply(fraction, floor());

		pay(actualInvestment);

		entry.addToInvestedCost(actualInvestment);
	}

	private void pay(final ProductionCost cost) {
		// Remove from planet:
		cost.getItems().stream().forEach(item -> item.getType().deduct(game, planet, item.getAmount()));
		// Remove from available resources for production:
		remainingResources = remainingResources.subtract(cost);
	}

	private void produce(final PlanetProductionResultBuilder planetResultBuilder, final ProductionQueueEntry entry,
			final int numberOfItems) {
		// Report:
		planetResultBuilder.produce(entry.getType(), numberOfItems);
		// Do the actual building:
		entry.getType().produce(numberOfItems);
		// Update the entry, so the the items won't be built again:
		final int remainingNumberToBuild = entry.getNumberOfItemsToBuild() - numberOfItems;
		entry.setNumberOfItemsToBuild(remainingNumberToBuild);
	}

	private static Function<Double, Integer> floor() {
		return input -> (int) Math.floor(input);
	}
}