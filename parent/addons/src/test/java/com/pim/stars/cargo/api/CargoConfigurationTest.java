package com.pim.stars.cargo.api;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.core.IsIterableContaining;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.CargoTestConfiguration;
import com.pim.stars.cargo.api.CargoConfigurationTest.FirstCargoTestConfiguration;
import com.pim.stars.cargo.api.CargoConfigurationTest.SecondCargoTestConfiguration;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.api.policies.CargoType.CargoTypeFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CargoTestConfiguration.WithoutPersistence.class, FirstCargoTestConfiguration.class,
		SecondCargoTestConfiguration.class })
@ActiveProfiles("WithoutPersistence")
public class CargoConfigurationTest {

	@Autowired(required = false)
	private final Collection<CargoType> cargoTypes = new ArrayList<>();

	@Test
	public void testThatApplicationContextStartsWithCargoTypes() {
		final List<String> cargoTypeIds = cargoTypes.stream().map(CargoType::getId).sorted()
				.collect(Collectors.toList());
		assertThat(cargoTypeIds, IsIterableContaining.hasItems("TypeA", "TypeB", "TypeC"));
	}

	@Configuration
	public static class FirstCargoTestConfiguration {

		@Bean
		public CargoTypeFactory firstCargoTypeFactory() {
			return () -> Arrays.asList(() -> "TypeA", () -> "TypeB");
		}
	}

	@Configuration
	public static class SecondCargoTestConfiguration {

		@Bean
		public CargoTypeFactory secondCargoTypeFactory() {
			return () -> Arrays.asList(() -> "TypeC");
		}
	}
}