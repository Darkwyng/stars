package com.pim.stars.production;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.resource.api.ResourceCalculator;

@Configuration
@Import({ ProductionConfiguration.Provided.class })
public class ProductionTestConfiguration implements ProductionConfiguration.Required {

	@Bean
	@Override
	public ResourceCalculator resourceCalculator() {
		return mock(ResourceCalculator.class);
	}

	@Bean
	@Override
	public EffectCalculator effectCalculator() {
		return mock(EffectCalculator.class);
	}

	@Bean
	@Override
	public EffectExecutor effectExecutor() {
		return mock(EffectExecutor.class);
	}

	@Override
	@Bean
	public PlanetProvider planetProvider() {
		return mock(PlanetProvider.class);
	}
}