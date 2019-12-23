package com.pim.stars.planets;

import static org.mockito.Mockito.mock;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.imp.persistence.PlanetRepository;
import com.pim.stars.race.api.RaceProvider;

public class PlanetTestConfiguration implements PlanetConfiguration.Required {

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Bean
	@Override
	public RaceProvider raceProvider() {
		return mock(RaceProvider.class);
	}

	@Configuration
	@Import({ PlanetTestConfiguration.class, PlanetConfiguration.Provided.class })
	public class WithoutPersistence {

		@MockBean
		private PlanetRepository planetRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ PlanetTestConfiguration.class, PlanetConfiguration.Provided.class })
	public class WithPersistence {

	}
}