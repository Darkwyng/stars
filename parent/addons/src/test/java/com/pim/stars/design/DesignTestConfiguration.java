package com.pim.stars.design;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.design.imp.persistence.DesignRepository;

public class DesignTestConfiguration {

	@Configuration
	@Import({ DesignTestConfiguration.class, DesignConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private DesignRepository designRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ DesignTestConfiguration.class, DesignConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}
