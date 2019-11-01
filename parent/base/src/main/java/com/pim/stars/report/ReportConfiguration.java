package com.pim.stars.report;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.report.imp.persistence.ReportRepository;

public interface ReportConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { ReportRepository.class })
	public static class Provided {

	}

	@Configuration
	@Import({}) // Currently nothing is imported by this component
	public static class Complete extends Provided {

	}

	public static interface Required {
		// Currently nothing is required by this component
	}
}
