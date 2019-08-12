package com.pim.stars.colonization.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.colonization.ColonizationTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ColonizationTestConfiguration.class)
public class ColonizationConfigurationTest {

	@Autowired
	private ColonistCalculator colonistCalculator;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(colonistCalculator, not(nullValue()));
		new ColonizationConfiguration.Complete(); // (for test coverage)
	}
}