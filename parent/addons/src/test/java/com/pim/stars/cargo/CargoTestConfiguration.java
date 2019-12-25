package com.pim.stars.cargo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.cargo.imp.persistence.CargoRepository;

public class CargoTestConfiguration implements CargoConfiguration.Required {

	@Configuration
	@Import({ CargoTestConfiguration.class, CargoConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private CargoRepository cargoRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ CargoTestConfiguration.class, CargoConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}