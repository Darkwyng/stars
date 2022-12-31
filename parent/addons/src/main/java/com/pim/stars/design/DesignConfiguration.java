package com.pim.stars.design;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.design.imp.persistence.DesignRepository;
import com.pim.stars.id.IdConfiguration;
import com.pim.stars.id.api.IdCreator;

public interface DesignConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { DesignRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class, IdConfiguration.Complete.class })
	public static class Complete {

	}

	public static interface Required {

		public IdCreator idCreator();
	}
}