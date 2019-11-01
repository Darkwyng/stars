package com.pim.stars.mineral;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.CargoConfiguration;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonizationConfiguration;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectConfiguration;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.api.PlanetConfiguration;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetCargo;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.production.ProductionConfiguration;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.race.api.RaceConfiguration;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.report.ReportConfiguration;
import com.pim.stars.report.api.ReportCreator;

public interface MineralConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

	}

	@Configuration
	@Import({ EffectConfiguration.Complete.class, PlanetConfiguration.Complete.class, CargoConfiguration.Complete.class,
			RaceConfiguration.Complete.class, ProductionConfiguration.Complete.class,
			ColonizationConfiguration.Complete.class, ReportConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public EffectCalculator effectCalculator();

		public CargoProcessor cargoProcessor();

		public PlanetOwnerId planetOwnerId();

		public PlanetName planetName();

		public GameRaceCollection gameRaceCollection();

		public RaceId raceId();

		public ProductionAvailabilityCalculator productionAvailabilityCalculator();

		public GamePlanetCollection gamePlanetCollection();

		public PlanetCargo planetCargo();

		public IdCreator idCreator();

		public ReportCreator reportCreator();
	}
}
