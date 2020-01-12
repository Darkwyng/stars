package com.pim.stars.production.imp.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.ProductionTestConfiguration;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.api.policies.ProductionItemType.ProductionItemTypeFactory;
import com.pim.stars.production.imp.persistence.ProductionPersistenceInterfaceTest.TestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("WithPersistence")
public class ProductionPersistenceInterfaceTest {

	@Autowired
	private ProductionPersistenceInterface testee;
	@Autowired
	private Collection<ProductionItemType> productionItemTypes;

	@Test
	public void testThatNewItemIsAddedToPreviouslyEmptyQueue() {
		final Game game = mock(Game.class);
		when(game.getId()).thenReturn("theGameId");
		final Planet planet = mock(Planet.class);
		when(planet.getName()).thenReturn("thePlanetName");
		final ProductionItemType itemType = productionItemTypes.iterator().next();

		assertThat(testee.getQueues(game).count(), is(0L));
		testee.addToQueue(game, planet, itemType, 17);
		assertThat(testee.getQueues(game).count(), is(1L));
		testee.addToQueue(game, planet, itemType, 18);
		assertThat(testee.getQueues(game).count(), is(1L));
	}

	@Configuration
	@Import({ ProductionTestConfiguration.WithPersistence.class })
	protected static class TestConfiguration {

		@Bean
		public ProductionItemTypeFactory anotherProductionItemTypeFactory() {
			return () -> Arrays.asList(mockItemType("anItemType"));
		}

		private ProductionItemType mockItemType(final String id) {
			final ProductionItemType result = mock(ProductionItemType.class);
			when(result.getId()).thenReturn(id);
			return result;
		}
	}
}
