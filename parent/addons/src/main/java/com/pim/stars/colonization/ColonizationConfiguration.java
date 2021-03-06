package com.pim.stars.colonization;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.CargoConfiguration;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.effect.EffectConfiguration;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.PlanetConfiguration;
import com.pim.stars.planets.api.PlanetProcessor;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.race.RaceConfiguration;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;

public interface ColonizationConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class, PlanetConfiguration.Complete.class, CargoConfiguration.Complete.class,
			RaceConfiguration.Complete.class, EffectConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public CargoProcessor cargoProcessor();

		public EffectCalculator effectCalculator();

		public EffectExecutor effectExecutor();

		public IdCreator idCreator();

		public GameInitializationDataRaceCollection gameInitializationDataRaceCollection();

		public RaceProvider raceProvider();

		public PlanetProvider planetProvider();

		public PlanetProcessor planetProcessor();
	}
}