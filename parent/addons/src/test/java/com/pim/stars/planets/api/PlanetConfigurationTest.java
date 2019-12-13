package com.pim.stars.planets.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.planets.PlanetTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PlanetTestConfiguration.WithoutPersistence.class)
public class PlanetConfigurationTest {

	@Test
	public void testThatApplicationContextStarts() {
	}
}
