package com.pim.stars.production;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.planets.PlanetConfiguration;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.resource.ResourceConfiguration;
import com.pim.stars.resource.api.ResourceCalculator;

public interface ProductionConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class, PlanetConfiguration.Complete.class, ResourceConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public GamePlanetCollection gamePlanetCollection();

		public ResourceCalculator resourceCalculator();

		public EffectCalculator effectCalculator();

		public EffectExecutor effectExecutor();
	}
}