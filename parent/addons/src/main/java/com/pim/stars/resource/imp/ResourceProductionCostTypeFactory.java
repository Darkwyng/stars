package com.pim.stars.resource.imp;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;
import com.pim.stars.resource.api.ResourceProductionCostTypeProvider;

@Component
public class ResourceProductionCostTypeFactory
		implements ProductionCostTypeFactory, ResourceProductionCostTypeProvider {

	private ProductionCostType resourceProductionCostType;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Collection<ProductionCostType> createProductionCostTypes() {
		return Arrays.asList(getResourceProductionCostType());
	}

	@Override
	public ProductionCostType getResourceProductionCostType() {
		if (resourceProductionCostType == null) {
			resourceProductionCostType = new ResourceProductionCostType();
			applicationContext.getAutowireCapableBeanFactory().autowireBean(resourceProductionCostType);
		}

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