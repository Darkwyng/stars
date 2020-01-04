package com.pim.stars.location;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.location.imp.persistence.LocationRepository;

public class LocationTestConfiguration {

	@Configuration
	@Import({ LocationTestConfiguration.class, LocationConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private LocationRepository locationRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ LocationTestConfiguration.class, LocationConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}
