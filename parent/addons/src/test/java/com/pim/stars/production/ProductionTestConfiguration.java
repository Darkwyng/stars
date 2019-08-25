package com.pim.stars.production;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.resource.api.ResourceCalculator;

@Configuration
@Import({ ProductionConfiguration.Provided.class })
public class ProductionTestConfiguration implements ProductionConfiguration.Required {

	@Override
	public GamePlanetCollection gamePlanetCollection() {
		return mock(GamePlanetCollection.class);
	}

	@Override
	public ResourceCalculator resourceCalculator() {
		return mock(ResourceCalculator.class);
	}
}