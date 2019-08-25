package com.pim.stars.production;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.planets.api.PlanetConfiguration;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.resource.ResourceConfiguration;
import com.pim.stars.resource.api.ResourceCalculator;

public interface ProductionConfiguration {

	@Configuration
	@ComponentScan
	public static class Provided {

	}

	@Configuration
	@Import({ PlanetConfiguration.Complete.class, ResourceConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public GamePlanetCollection gamePlanetCollection();

		public ResourceCalculator resourceCalculator();
	}
}