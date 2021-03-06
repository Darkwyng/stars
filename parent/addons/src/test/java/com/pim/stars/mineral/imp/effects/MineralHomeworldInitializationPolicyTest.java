package com.pim.stars.mineral.imp.effects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MineProductionItemTypeProvider;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;

public class MineralHomeworldInitializationPolicyTest {

	@Mock
	private ProductionAvailabilityCalculator productionAvailabilityCalculator;
	@Mock
	private MineProductionItemTypeProvider mineProductionItemTypeProvider;
	@Mock
	private MineralProperties mineralProperties;

	@InjectMocks
	private MineralHomeworldInitializationPolicy testee;
	@Mock
	private Game game;
	@Mock
	private Planet planet;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(mineralProperties.getNumberOfMinesToStartWith()).thenReturn(7);
	}

	@Test
	public void testThatNoMinesAreBuiltForRacesWithoutMines() {
		when(productionAvailabilityCalculator.isProductionItemTypeAvailable(eq(game), eq(planet), any()))
				.thenReturn(false);

		final int mineCount = testee.getMineCountForHomeworld(game, planet);
		assertThat(mineCount, is(0));
	}

	@Test
	public void testThatMinesAreBuiltForRacesWithMines() {
		when(productionAvailabilityCalculator.isProductionItemTypeAvailable(eq(game), eq(planet), any()))
				.thenReturn(true);

		final int mineCount = testee.getMineCountForHomeworld(game, planet);
		assertThat(mineCount, is(7));
	}
}
