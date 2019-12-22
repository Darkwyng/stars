package com.pim.stars.planets.imp.effects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.planets.api.extensions.GameInitializationDataNumberOfPlanets;
import com.pim.stars.planets.imp.PlanetProperties;

public class PlanetGameInitializationPolicyTest {

	@Mock
	private GameInitializationDataNumberOfPlanets gameInitializationDataNumberOfPlanets;
	@Mock
	private PlanetProperties planetProperties;
	@Mock
	private GameInitializationData gameInitializationData;

	@InjectMocks
	private PlanetGameInitializationPolicy testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testThatNotEnoughPlanetNamesIsRecognized() {
		when(gameInitializationDataNumberOfPlanets.getValue(gameInitializationData)).thenReturn(5);
		when(planetProperties.getNames()).thenReturn(Arrays.asList("1", "2", "3", "4"));
		assertThrows(IllegalArgumentException.class, () -> testee.getAvailablePlanetNames(gameInitializationData));
	}
}