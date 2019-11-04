package com.pim.stars.colonization.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.CargoConfiguration;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.policies.ColonistCargoType;
import com.pim.stars.colonization.imp.ColonistCalculatorImp;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.colonization.imp.effects.ColonistGrowthGameGenerationPolicy;
import com.pim.stars.colonization.imp.effects.ColonistHomeworldInitializationPolicy;
import com.pim.stars.colonization.imp.effects.ColonistResourceCalculationPolicy;
import com.pim.stars.colonization.imp.effects.DefaultPlanetCapacityPolicy;
import com.pim.stars.colonization.imp.effects.HomeworldGameInitializationPolicy;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.effect.EffectConfiguration;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.PlanetConfiguration;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.race.RaceConfiguration;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.turn.api.Race;

public interface ColonizationConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public ColonistCalculator colonistCalculator() {
			return new ColonistCalculatorImp();
		}

		@Bean
		public ColonistCargoType colonistCargoTypePolicy() {
			return new ColonistCargoType();
		}

		@Bean
		public ColonistGrowthGameGenerationPolicy colonistGrowthGameGenerationPolicy() {
			return new ColonistGrowthGameGenerationPolicy();
		}

		@Bean
		public ColonistHomeworldInitializationPolicy colonistHomeworldInitializationPolicy() {
			return new ColonistHomeworldInitializationPolicy();
		}

		@Bean
		public HomeworldGameInitializationPolicy homeworldGameInitializationPolicy() {
			return new HomeworldGameInitializationPolicy();
		}

		@Bean
		public DefaultPlanetCapacityPolicy defaultPlanetCapacityPolicy() {
			return new DefaultPlanetCapacityPolicy();
		}

		@Bean
		public ColonizationProperties colonizationProperties() {
			return new ColonizationProperties();
		}

		@Bean
		public ColonistResourceCalculationPolicy colonistResourceCalculationPolicy() {
			return new ColonistResourceCalculationPolicy();
		}
	}

	@Configuration
	@Import({ PlanetConfiguration.Complete.class, CargoConfiguration.Complete.class, RaceConfiguration.Complete.class,
			EffectConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public GamePlanetCollection gamePlanetCollection();

		public GameRaceCollection gameRaceCollection();

		public PlanetOwnerId planetOwnerId();

		public DataExtensionPolicy<Race, String> raceId();

		public CargoProcessor cargoProcessor();

		public EffectCalculator effectCalculator();

		public EffectExecutor effectExecutor();

		public IdCreator idCreator();
	}
}