package com.pim.stars.race.imp;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.effects.PlanetCapacityPolicy;
import com.pim.stars.colonization.api.policies.ColonistCargoType;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.colonization.imp.effects.LowStartingPopulationHomeworldInitializationPolicy;
import com.pim.stars.effect.api.Effect;
import com.pim.stars.race.RaceTestConfiguration;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RaceTraitProviderImpTest.TestConfiguration.class)
public class RaceTraitProviderImpTest {

	@Autowired
	private RaceTraitProvider raceTraitProvider;

	@Test
	public void testThatPrimaryRacialTraitCollectionIsLoaded() {
		final Collection<PrimaryRacialTrait> traitCollection = raceTraitProvider.getPrimaryRacialTraitCollection();
		assertThat(traitCollection, allOf(not(empty()), not(nullValue())));

		final Optional<PrimaryRacialTrait> expectedTrait = raceTraitProvider.getPrimaryRacialTraitById("HyperExpander");
		assertThat(expectedTrait.isPresent(), is(true));

		final Optional<Effect> expectedPolicy = expectedTrait.get().getEffectCollection().stream()
				.filter(effect -> effect instanceof PlanetCapacityPolicy).findAny();
		assertThat(expectedPolicy.isPresent(), is(true));
	}

	@Test
	public void testThatSecondaryRacialTraitCollectionIsLoaded() {
		final Collection<SecondaryRacialTrait> traitCollection = raceTraitProvider.getSecondaryRacialTraitCollection();
		assertThat(traitCollection, allOf(not(empty()), not(nullValue())));

		final Optional<SecondaryRacialTrait> expectedTrait = raceTraitProvider
				.getSecondaryRacialTraitById("LowStartingPopulation");
		assertThat(expectedTrait.isPresent(), is(true));

		final Optional<Effect> expectedPolicy = expectedTrait.get().getEffectCollection().stream()
				.filter(effect -> effect instanceof LowStartingPopulationHomeworldInitializationPolicy).findAny();
		assertThat(expectedPolicy.isPresent(), is(true));

		final LowStartingPopulationHomeworldInitializationPolicy policy = (LowStartingPopulationHomeworldInitializationPolicy) expectedPolicy
				.get();
		assertThat(policy.getInitialPopulation(), is(175));
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