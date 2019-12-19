package com.pim.stars.cargo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.api.policies.CargoType.CargoTypeFactory;
import com.pim.stars.cargo.imp.persistence.CargoRepository;

public interface CargoConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	@EnableMongoRepositories(basePackageClasses = { CargoRepository.class })
	public static class Provided {

		@Bean
		public List<CargoType> cargoTypes(final Collection<CargoTypeFactory> factories) {
			return factories.stream().map(CargoTypeFactory::createCargoTypes).flatMap(Collection<CargoType>::stream)
					.collect(Collectors.toList());
		}
	}

	@Configuration
	@Import({ Provided.class })
	public static class Complete {

	}

	public static interface Required {
		// Currently nothing is required by this component
	}
}