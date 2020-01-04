package com.pim.stars.location;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.location.imp.persistence.LocationRepository;

public interface LocationConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { LocationRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({ Provided.class })
	public static class Complete {

	}
}