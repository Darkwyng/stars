package com.pim.stars.mineral.imp.policies;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.pim.stars.planets.api.Planet;

public class MineProductionItemTypeTest {

	private final MineProductionItemType testee = new MineProductionItemType();

	@Test
	public void testPlanetOwnershipIsChecked() {
		final Planet planet = mock(Planet.class);
		when(planet.getOwnerId()).thenReturn(Optional.empty());
		assertThrows(IllegalStateException.class, () -> testee.getOwnerId(planet));
	}
}