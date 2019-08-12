package com.pim.stars.race.imp.policies;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.policies.ColonistCargoType;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.effect.api.Effect;
import com.pim.stars.race.RaceTestConfiguration;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.RaceInitializer;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecondaryRacialTraitEffectProviderPolicyTest.TestConfiguration.class)
public class SecondaryRacialTraitEffectProviderPolicyTest {

	@Autowired
	private SecondaryRacialTraitEffectProviderPolicy testee;
	@Autowired
	private RaceSecondaryRacialTraitCollection raceSecondaryRacialTraitCollection;
	@Autowired
	private RaceInitializer raceInitializer;
	@Autowired
	private RaceTraitProvider raceTraitProvider;

	@Test
	public void testThatEffectHolderIsMatched() {
		assertThat(testee.matchesEffectHolder(mock(Race.class)), is(true));
		assertThat(testee.matchesEffectHolder(new Object()), is(false));
	}

	@Test
	public void testThatEffectsOfTraitAreReturned() {
		final Race race = raceInitializer.initializeRace();
		final SecondaryRacialTrait trait = raceTraitProvider.getSecondaryRacialTraitById("LowStartingPopulation").get();
		raceSecondaryRacialTraitCollection.setValue(race, Collections.singleton(trait));
		assertThat(trait.getEffectCollection(), not(empty()));
		final Class<? extends Effect> effectClass = trait.getEffectCollection().iterator().next().getClass();

		final Collection<? extends Effect> effectCollection = testee.getEffectCollectionFromEffectHolder(race,
				effectClass);
		assertThat(effectCollection, not(empty()));
	}

	/**
	 * These beans are required by the effects that are loaded from the XML files.
	 */
	@Configuration
	protected static class TestConfiguration extends RaceTestConfiguration {

		@Bean
		public CargoProcessor cargoProcessor() {
			return mock(CargoProcessor.class);
		}

		@Bean
		public ColonistCargoType colonistCargoType() {
			return mock(ColonistCargoType.class);
		}

		@Bean
		public ColonizationProperties colonizationProperties() {
			return mock(ColonizationProperties.class);
		}
	}
}