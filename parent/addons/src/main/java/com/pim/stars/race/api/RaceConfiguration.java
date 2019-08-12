package com.pim.stars.race.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.DataExtensionConfiguration;
import com.pim.stars.id.api.IdConfiguration;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;
import com.pim.stars.race.imp.RaceInitializerImp;
import com.pim.stars.race.imp.RaceProperties;
import com.pim.stars.race.imp.RaceTraitProviderImp;
import com.pim.stars.race.imp.effects.RaceGameInitializationPolicy;
import com.pim.stars.race.imp.policies.PrimaryRacialTraitEffectProviderPolicy;
import com.pim.stars.race.imp.policies.SecondaryRacialTraitEffectProviderPolicy;

public interface RaceConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public RaceInitializer raceInitializer() {
			return new RaceInitializerImp();
		}

		@Bean
		public RaceTraitProvider raceTraitProvider() {
			return new RaceTraitProviderImp();
		}

		@Bean
		public GameInitializationDataRaceCollection gameInitializationDataRaceCollection() {
			return new GameInitializationDataRaceCollection();
		}

		@Bean
		public GameRaceCollection gameRaceCollection() {
			return new GameRaceCollection();
		}

		@Bean
		public RaceId raceId() {
			return new RaceId();
		}

		@Bean
		public RacePrimaryRacialTrait racePrimaryRacialTrait() {
			return new RacePrimaryRacialTrait();
		}

		@Bean
		public RaceSecondaryRacialTraitCollection raceSecondaryRacialTraitCollection() {
			return new RaceSecondaryRacialTraitCollection();
		}

		@Bean
		public PrimaryRacialTraitEffectProviderPolicy raceEffectProviderPolicy() {
			return new PrimaryRacialTraitEffectProviderPolicy();
		}

		@Bean
		public SecondaryRacialTraitEffectProviderPolicy secondaryRacialTraitEffectProviderPolicy() {
			return new SecondaryRacialTraitEffectProviderPolicy();
		}

		@Bean
		public RaceGameInitializationPolicy raceGameInitializationPolicy() {
			return new RaceGameInitializationPolicy();
		}

		@Bean
		public RaceProperties raceProperties() {
			return new RaceProperties();
		}
	}

	@Configuration
	@Import({ IdConfiguration.Complete.class, DataExtensionConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public IdCreator idCreator();

		public DataExtender dataExtender();
	}
}