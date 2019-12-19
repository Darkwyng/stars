package com.pim.stars.resource.imp;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;
import com.pim.stars.resource.api.ResourceProductionCostTypeProvider;

@Component
public class ResourceProductionCostTypeFactory implements ProductionCostTypeFactory, ResourceProductionCostTypeProvider {

	private final ProductionCostType resourceProductionCostType = new ResourceProductionCostType();

	@Override
	public Collection<ProductionCostType> createProductionCostTypes() {
		return Arrays.asList(resourceProductionCostType);
	}

	@Override
	public ProductionCostType getResourceProductionCostType() {
		return resourceProductionCostType;
	}

	private class ResourceProductionCostType implements ProductionCostType {

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
}