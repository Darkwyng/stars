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
import com.pim.stars.turn.TurnConfiguration;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder;

public interface RaceConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { RaceRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ IdConfiguration.Complete.class, DataExtensionConfiguration.Complete.class,
			TurnConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public IdCreator idCreator();

		public DataExtender dataExtender();

		public GameToTurnTransformerBuilder gameToTurnTransformerBuilder();
	}
}