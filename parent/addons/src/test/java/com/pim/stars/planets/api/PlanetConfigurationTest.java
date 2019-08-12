package com.pim.stars.planets.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.planets.PlanetTestConfiguration;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PlanetTestConfiguration.class)
public class PlanetConfigurationTest {

	@Autowired
	private GamePlanetCollection gamePlanetCollection;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(gamePlanetCollection, not(nullValue()));
		new PlanetConfiguration.Complete(); // (for test coverage)
	}
}
