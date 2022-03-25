package com.pim.stars.planets;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.cargo.api.CargoItemProvider;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.location.api.LocationInitializer;
import com.pim.stars.planets.imp.persistence.PlanetRepository;
import com.pim.stars.race.api.RaceProvider;

public class PlanetTestConfiguration {

	@Bean
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Bean
	public RaceProvider raceProvider() {
		return mock(RaceProvider.class);
	}

	@Bean
	public LocationInitializer locationInitializer() {
		return mock(LocationInitializer.class);
	}

	@Bean
	public CargoItemProvider cargoItemProvider() {
		return mock(CargoItemProvider.class);
	}

	@Bean
	public Collection<CargoType> cargoTypeCollection() {
		return new ArrayList<>();
	}

	@Configuration
	@Import({ PlanetTestConfiguration.class, PlanetConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private PlanetRepository planetRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ PlanetTestConfiguration.class, PlanetConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}