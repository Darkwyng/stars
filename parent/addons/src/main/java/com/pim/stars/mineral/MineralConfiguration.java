package com.pim.stars.mineral;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.cargo.CargoConfiguration;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.ColonizationConfiguration;
import com.pim.stars.effect.EffectConfiguration;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.mineral.imp.persistence.MineralRaceRepository;
import com.pim.stars.planets.PlanetConfiguration;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.ProductionConfiguration;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.race.RaceConfiguration;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.report.ReportConfiguration;
import com.pim.stars.report.api.ReportCreator;

public interface MineralConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { MineralRaceRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class, EffectConfiguration.Complete.class, PlanetConfiguration.Complete.class,
			CargoConfiguration.Complete.class, RaceConfiguration.Complete.class, ProductionConfiguration.Complete.class,
			ColonizationConfiguration.Complete.class, ReportConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public EffectCalculator effectCalculator();

		public CargoProcessor cargoProcessor();

		public ProductionAvailabilityCalculator productionAvailabilityCalculator();

		public GameInitializationDataRaceCollection gameInitializationDataRaceCollection();

		public IdCreator idCreator();

		public ReportCreator reportCreator();

		public RaceProvider raceProvider();

		public PlanetProvider planetProvider();
	}
}
