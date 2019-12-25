package com.pim.stars.report;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.report.imp.persistence.ReportRepository;

public class ReportTestConfiguration implements ReportConfiguration.Required {

	@Configuration
	@Import({ ReportTestConfiguration.class, ReportConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private ReportRepository repository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ ReportTestConfiguration.class, ReportConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}