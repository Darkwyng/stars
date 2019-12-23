package com.pim.stars.game;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.dataextension.DataExtensionConfiguration;
import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.EffectConfiguration;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.imp.persistence.GameRepository;
import com.pim.stars.id.IdConfiguration;
import com.pim.stars.id.api.IdCreator;

public interface GameConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { GameRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class, DataExtensionConfiguration.Complete.class, EffectConfiguration.Complete.class,
			IdConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public DataExtender dataExtender();

		public EffectExecutor effectExecutor();

		public IdCreator idCreator();
	}
}
