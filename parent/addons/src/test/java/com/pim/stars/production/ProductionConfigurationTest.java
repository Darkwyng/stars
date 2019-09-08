package com.pim.stars.production;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.production.imp.effects.PlanetProductionGameGenerationPolicy;
import com.pim.stars.production.imp.extensions.PlanetProductionQueue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductionTestConfiguration.class)
public class ProductionConfigurationTest {

	@Autowired
	private PlanetProductionGameGenerationPolicy planetProductionGameGenerationPolicy;
	@Autowired
	private PlanetProductionQueue planetProductionQueue;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(planetProductionGameGenerationPolicy, not(nullValue()));
		assertThat(planetProductionQueue, not(nullValue()));
		new ProductionConfiguration.Complete(); // (for test coverage)
	}
}