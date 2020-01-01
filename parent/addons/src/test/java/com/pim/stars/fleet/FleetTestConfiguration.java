package com.pim.stars.fleet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.fleet.imp.persistence.FleetRepository;

public class FleetTestConfiguration {

	@Configuration
	@Import({ FleetTestConfiguration.class, FleetConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private FleetRepository fleetRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ FleetTestConfiguration.class, FleetConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}
