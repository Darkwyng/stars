package com.pim.stars.colonization;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.CargoConfiguration;
import com.pim.stars.cargo.api.CargoProcessor;
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
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

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