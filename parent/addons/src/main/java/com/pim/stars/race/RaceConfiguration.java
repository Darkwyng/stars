package com.pim.stars.race;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.dataextension.DataExtensionConfiguration;
import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.id.IdConfiguration;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.imp.persistence.RaceRepository;

public interface RaceConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { RaceRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class, IdConfiguration.Complete.class, DataExtensionConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public IdCreator idCreator();

		public DataExtender dataExtender();
	}
}