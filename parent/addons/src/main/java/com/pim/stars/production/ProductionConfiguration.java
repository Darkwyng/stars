package com.pim.stars.production;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.planets.PlanetConfiguration;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;
import com.pim.stars.resource.ResourceConfiguration;
import com.pim.stars.resource.api.ResourceCalculator;

public interface ProductionConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

		@Bean
		public List<ProductionCostType> productionCostTypes(final Collection<ProductionCostTypeFactory> factories) {
			return factories.stream().map(ProductionCostTypeFactory::createProductionCostTypes)
					.flatMap(Collection<ProductionCostType>::stream).collect(toList());
		}
	}

	@Configuration
	@Import({ Provided.class, PlanetConfiguration.Complete.class, ResourceConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public ResourceCalculator resourceCalculator();

		public EffectCalculator effectCalculator();

		public EffectExecutor effectExecutor();

		public PlanetProvider planetProvider();
	}
}