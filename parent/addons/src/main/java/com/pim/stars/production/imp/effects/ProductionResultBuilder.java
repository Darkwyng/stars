package com.pim.stars.production.imp.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.policies.ProductionItemType;

public class ProductionResultBuilder {

	private final Collection<PlanetProductionResult> results = new ArrayList<>();

	public PlanetProductionResultBuilder startPlanet(final Planet planet) {
		return new PlanetProductionResultBuilder(this, planet);
	}

	protected ProductionResultBuilder finishPlanet(final PlanetProductionResult planetProductionResult) {
		results.add(planetProductionResult);
		return this;
	}

	public Collection<PlanetProductionResult> finish() {
		return results;
	}

	public static class PlanetProductionResultBuilder {

		private final ProductionResultBuilder baseBuilder;
		private final Planet planet;
		private final List<ProducedItem> producedItems = new ArrayList<>();

		public PlanetProductionResultBuilder(final ProductionResultBuilder baseBuilder, final Planet planet) {
			this.baseBuilder = baseBuilder;
			this.planet = planet;
		}

		public PlanetProductionResultBuilder produce(final ProductionItemType type, final int numberOfItems) {
			producedItems.add(new ProducedItem(type, numberOfItems));
			return this;
		}

		public ProductionResultBuilder finishPlanet(final ProductionCost remainingResources) {
			return baseBuilder.finishPlanet(new PlanetProductionResult(planet, producedItems, remainingResources));
		}
	}

	public static class ProducedItem {

		private final ProductionItemType type;
		private final int numberOfItems;

		public ProducedItem(final ProductionItemType type, final int numberOfItems) {
			this.type = type;
			this.numberOfItems = numberOfItems;
		}

		public ProductionItemType getItemType() {
			return type;
		}

		public int getNumberOfItems() {
			return numberOfItems;
		}
	}

	public static class PlanetProductionResult {

		private final Planet planet;
		private final List<ProducedItem> producedItems;
		private final ProductionCost remainingResources;

		public PlanetProductionResult(final Planet planet, final List<ProducedItem> producedItems,
				final ProductionCost remainingResources) {
			this.planet = planet;
			this.producedItems = producedItems;
			this.remainingResources = remainingResources;
		}

		public Planet getPlanet() {
			return planet;
		}

		public List<ProducedItem> getProducedItems() {
			return producedItems;
		}

		public ProductionCost getRemainingResources() {
			return remainingResources;
		}
	}
}
