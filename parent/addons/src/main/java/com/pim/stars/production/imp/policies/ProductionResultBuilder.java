package com.pim.stars.production.imp.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pim.stars.planets.api.Planet;
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
		private final List<ProducedItems> producedItems = new ArrayList<>();

		public PlanetProductionResultBuilder(final ProductionResultBuilder baseBuilder, final Planet planet) {
			this.baseBuilder = baseBuilder;
			this.planet = planet;
		}

		public PlanetProductionResultBuilder produce(final ProductionItemType type, final int numberOfItems) {
			producedItems.add(new ProducedItems(type, numberOfItems));
			return this;
		}

		public ProductionResultBuilder finishPlanet(final int remainingResources) {
			return baseBuilder.finishPlanet(new PlanetProductionResult(planet, producedItems, remainingResources));
		}
	}

	public static class ProducedItems {

		private final ProductionItemType type;
		private final int numberOfItems;

		public ProducedItems(final ProductionItemType type, final int numberOfItems) {
			this.type = type;
			this.numberOfItems = numberOfItems;
		}

		public ProductionItemType getType() {
			return type;
		}

		public int getNumberOfItems() {
			return numberOfItems;
		}
	}

	public static class PlanetProductionResult {

		private final Planet planet;
		private final List<ProducedItems> producedItems;
		private final int remainingResources;

		public PlanetProductionResult(final Planet planet, final List<ProducedItems> producedItems,
				final int remainingResources) {
			this.planet = planet;
			this.producedItems = producedItems;
			this.remainingResources = remainingResources;
		}

		public Planet getPlanet() {
			return planet;
		}

		public List<ProducedItems> getProducedItems() {
			return producedItems;
		}

		public int getRemainingResources() {
			return remainingResources;
		}
	}
}
