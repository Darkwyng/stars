package com.pim.stars.resource;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;

public class ResourceTestConfiguration implements ResourceConfiguration.Required {

	/** To test that the resource production cost type can be autowired after the creation by its factory. */
	@Bean
	public List<ProductionCostType> productionCostTypes(final Collection<ProductionCostTypeFactory> factories) {
		return factories.stream().map(ProductionCostTypeFactory::createProductionCostTypes)
				.flatMap(Collection<ProductionCostType>::stream).collect(toList());
	}

	@Override
	@Bean
	public EffectCalculator effectCalculator() {
		return mock(EffectCalculator.class);
	}

	@Configuration
	@Import({ ResourceTestConfiguration.class, ResourceConfiguration.Provided.class })
	public class WithoutPersistence {

	}
}