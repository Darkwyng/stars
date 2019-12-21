package com.pim.stars.mineral.imp.effects;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.effects.MiningPolicy.MiningPolicyResult;
import com.pim.stars.planets.api.Planet;

public class MineMiningPolicyTest {

	@Mock
	private Game game;
	@Mock
	private Planet planet;

	@Mock
	private MiningCalculator miningCalculator;
	@Mock
	private CargoProcessor cargoProcessor;

	@InjectMocks
	private MineMiningPolicy testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testThatPlanetWithoutOwnerDoesNotMine() {
		final MiningPolicyResult result = testee.calculateMining(game, planet);
		result.getMinedCargo();
		verify(cargoProcessor).createCargoHolder();
		verifyNoMoreInteractions(miningCalculator);
	}

}
