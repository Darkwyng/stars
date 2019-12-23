package com.pim.stars.mineral.imp.effects;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.pim.stars.planets.api.Planet;

public class MineralGameGenerationPolicyTest {

	private final MineralGameGenerationPolicy testee = new MineralGameGenerationPolicy();

	@Test
	public void testReportIsNotCreatedWhenPlanetHasNoOwner() {
		final Planet planet = mock(Planet.class);
		testee.createReportIfNecessary(null, planet, null);
	}
}
