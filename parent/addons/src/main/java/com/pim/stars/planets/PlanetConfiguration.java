package com.pim.stars.planets;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.dataextension.DataExtensionConfiguration;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.imp.persistence.PlanetRepository;
import com.pim.stars.race.RaceConfiguration;
import com.pim.stars.race.api.RaceProvider;

public interface PlanetConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { PlanetRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class, DataExtensionConfiguration.Complete.class, RaceConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public IdCreator idCreator();

		public RaceProvider raceProvider();
	}
}