package com.pim.stars.race.imp;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.effects.PlanetCapacityPolicy;
import com.pim.stars.colonization.api.policies.ColonistCargoType;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.colonization.imp.effects.LowStartingPopulationHomeworldInitializationPolicy;
import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.race.RaceTestConfiguration;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RaceTraitProviderImpTest.TestConfiguration.class)
public class RaceTraitProviderImpTest {

	@Autowired
	private RaceTraitProvider raceTraitProvider;

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ColonistCargoType colonistCargoType;
	@Autowired
	private ColonizationProperties colonizationProperties;

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
	public void testThatSecondaryRacialTraitCollectionIsLoaded() throws Exception {
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

		// This will fail when the implementation changes, unfortunately:
		assertAll(() -> assertThat(getFieldValue(policy, "cargoProcessor"), sameInstance(cargoProcessor)), //
				() -> assertThat(getFieldValue(policy, "colonistCargoType"), sameInstance(colonistCargoType)), //
				() -> assertThat(getFieldValue(policy, "colonizationProperties"),
						sameInstance(colonizationProperties)));
	}

	private Object getFieldValue(final LowStartingPopulationHomeworldInitializationPolicy policy,
			final String fieldName) throws IllegalAccessException, NoSuchFieldException {
		final Optional<Field> optionalField = Arrays
				.stream(LowStartingPopulationHomeworldInitializationPolicy.class.getSuperclass().getDeclaredFields())
				.filter(candidate -> candidate.getName().equals(fieldName)).findAny();
		assertThat("Field " + fieldName + " should exist", optionalField.isPresent(), is(true));
		optionalField.get().setAccessible(true);
		return optionalField.get().get(policy);
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
		public ColonistCargoType colonistCargoType() {
			return mock(ColonistCargoType.class);
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