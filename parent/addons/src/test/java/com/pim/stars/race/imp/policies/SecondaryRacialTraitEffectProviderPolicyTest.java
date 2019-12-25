package com.pim.stars.race.imp.policies;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonistCargoTypeProvider;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.api.Game;
import com.pim.stars.race.RaceTestConfiguration;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;
import com.pim.stars.race.imp.RaceImp;
import com.pim.stars.race.imp.persistence.RaceEntity;
import com.pim.stars.race.imp.persistence.RaceRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecondaryRacialTraitEffectProviderPolicyTest.TestConfiguration.class)
@ActiveProfiles("WithoutPersistence")
public class SecondaryRacialTraitEffectProviderPolicyTest {

	@Autowired
	private SecondaryRacialTraitEffectProviderPolicy testee;
	@Autowired
	private RaceTraitProvider raceTraitProvider;
	@Autowired
	private RaceRepository raceRepository;

	@Test
	public void testThatEffectHolderIsMatched() {
		assertThat(testee.matchesEffectHolder(mock(Race.class)), is(true));
		assertThat(testee.matchesEffectHolder(new Object()), is(false));
	}

	@Test
	public void testThatEffectsOfTraitAreReturned() {
		final RaceEntity entity = new RaceEntity();
		entity.setSecondaryRacialTraitIds(Collections.singleton("LowStartingPopulation"));
		when(raceRepository.findByGameIdAndRaceId("aGameId", "exampleRaceId")).thenReturn(entity);

		final SecondaryRacialTrait trait = raceTraitProvider.getSecondaryRacialTraitById("LowStartingPopulation").get();
		assertThat(trait.getEffectCollection(), not(empty()));

		final Effect effect = trait.getEffectCollection().iterator().next();
		final Class<? extends Effect> effectClass = effect.getClass();

		final Game game = mock(Game.class);
		when(game.getId()).thenReturn("aGameId");

		final Collection<? extends Effect> effectCollection = testee.getEffectCollectionFromEffectHolder(game,
				new RaceImp("exampleRaceId"), effectClass);
		assertThat(effectCollection, containsInAnyOrder(effect));
	}

	/**
	 * These beans are required by the effects that are loaded from the XML files.
	 */
	@Configuration
	@Import({ RaceTestConfiguration.WithoutPersistence.class })
	protected static class TestConfiguration {

		@Bean
		public CargoProcessor cargoProcessor() {
			return mock(CargoProcessor.class);
		}

		@Bean
		public ColonistCargoTypeProvider colonistCargoTypeProvider() {
			return mock(ColonistCargoTypeProvider.class);
		}

		@Bean
		public ColonizationProperties colonizationProperties() {
			return mock(ColonizationProperties.class);
		}

		@Bean
		public EffectExecutor effectExecutor() {
			return mock(EffectExecutor.class);
		}
	}
}