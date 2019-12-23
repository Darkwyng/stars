package com.pim.stars.production;

import static org.mockito.Mockito.mock;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.imp.persistence.ProductionRepository;
import com.pim.stars.resource.api.ResourceCalculator;

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

	@Configuration
	@Import({ ProductionTestConfiguration.class, ProductionConfiguration.Provided.class })
	public class WithoutPersistence {

		@MockBean
		private ProductionRepository productionRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ ProductionTestConfiguration.class, ProductionConfiguration.Provided.class })
	public class WithPersistence {

	}
}