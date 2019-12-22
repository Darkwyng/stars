package com.pim.stars.mineral.imp.effects;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.effects.MiningPolicy.MiningPolicyResult;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;

public class MineMiningPolicyTest {

	@Mock
	private Game game;
	@Mock
	private Planet planet;

	@Mock
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;
	@Mock
	private ProductionAvailabilityCalculator productionAvailabilityCalculator;
	@Mock
	private MineProductionItemType mineProductionItemType;
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
		when(planet.getOwnerId()).thenReturn(Optional.empty());

		verifyNoMiningIsDone();
	}

	@Test
	public void testThatPlanetWithoutMinesDoesNotMine() {
		when(planet.getOwnerId()).thenReturn(Optional.of("aRaceId"));
		when(mineralPlanetPersistenceInterface.getMineCount(game, planet)).thenReturn(0);

		verifyNoMiningIsDone();
	}

	@Test
	public void testThatOwnerWithoutMiningRightsDoesNotMine() {
		when(planet.getOwnerId()).thenReturn(Optional.of("aRaceId"));
		when(mineralPlanetPersistenceInterface.getMineCount(game, planet)).thenReturn(7);
		when(productionAvailabilityCalculator.isProductionItemTypeAvailable(game, planet, mineProductionItemType))
				.thenReturn(false);

		verifyNoMiningIsDone();
	}

	private void verifyNoMiningIsDone() {
		final MiningPolicyResult result = testee.calculateMining(game, planet);
		result.getMinedCargo();

		verify(cargoProcessor).createCargoHolder();
		verifyNoMoreInteractions(miningCalculator);
	}

}
