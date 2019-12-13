package com.pim.stars.cargo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.cargo.imp.persistence.CargoRepository;

public interface CargoConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { CargoRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class })
	public static class Complete {

	}

	public static interface Required {
		// Currently nothing is required by this component
	}
}