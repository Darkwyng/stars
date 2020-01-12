package com.pim.stars.location;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pim.stars.location.imp.persistence.location.LocationRepository;
import com.pim.stars.location.imp.persistence.universe.UniverseRepository;

public class LocationTestConfiguration {

	@Configuration
	@Import({ LocationTestConfiguration.class, LocationConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private LocationRepository locationRepository;
		@MockBean
		private UniverseRepository universeRepository;
		@MockBean
		private MongoTemplate mongoTemplate;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ LocationTestConfiguration.class, LocationConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}
