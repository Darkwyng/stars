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
import com.pim.stars.resource.api.ResourceProductionCostTypeProvider;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ResourceTestConfiguration.class)
public class ResourceConfigurationTest {

	@Autowired
	private ResourceCalculator resourceCalculator;
	@Autowired
	private ResourceProductionCostTypeProvider resourceProductionCostTypeProvider;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(resourceCalculator, not(nullValue()));
		assertThat(resourceProductionCostTypeProvider, not(nullValue()));
	}
}