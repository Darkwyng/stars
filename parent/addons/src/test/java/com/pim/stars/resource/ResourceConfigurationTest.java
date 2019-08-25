package com.pim.stars.resource;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.resource.api.ResourceCalculator;
import com.pim.stars.resource.api.policies.ResourceProductionCostType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ResourceTestConfiguration.class)
public class ResourceConfigurationTest {

	@Autowired
	private ResourceCalculator resourceCalculator;
	@Autowired
	private ResourceProductionCostType resourceProductionCostType;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(resourceCalculator, not(nullValue()));
		assertThat(resourceProductionCostType, not(nullValue()));
		new ResourceConfiguration.Complete(); // (for test coverage)
	}
}