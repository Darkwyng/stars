package com.pim.stars.production.imp.effects;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.api.ProductionCostCalculator;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.ProductionQueueEntry;

public class PlanetProductionGameGenerationPolicyTest {

	@Mock
	private PlanetProvider planetProvider;
	@Mock
	private ProductionCostCalculator productionCostCalculator;

	@Mock
	private Game game;

	@InjectMocks
	private PlanetProductionGameGenerationPolicy testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(planetProvider.getPlanet(any(), any())).thenAnswer(inv -> mock(Planet.class));
	}

	@Test
	public void testThatEmptyQueueLeadtoNoProduction() {
		final ProductionQueue queue = new ProductionQueue("myPlanetName");
		final ProductionExecutor executor = testee.mapQueueToExecutor(game, queue);
		assertThat(executor, nullValue());
		verifyNoInteractions(productionCostCalculator);
	}

	@Test
	public void testNoAvailableResourcesLeadtoNoProduction() {
		final ProductionCost noAvailableResources = mock(ProductionCost.class);
		when(noAvailableResources.isZero()).thenReturn(true);

		when(productionCostCalculator.getProductionInputForPlanet(any(), any())).then(inv -> noAvailableResources);

		final ProductionQueue queue = new ProductionQueue("myPlanetName");
		queue.addEntry(new ProductionQueueEntry(mock(ProductionItemType.class)));
		final ProductionExecutor executor = testee.mapQueueToExecutor(game, queue);
		assertThat(executor, nullValue());
		verify(productionCostCalculator).getProductionInputForPlanet(any(), any());
	}
}