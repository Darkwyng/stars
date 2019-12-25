package com.pim.stars.dataextension;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

public class DataExtensionTestConfiguration implements DataExtensionConfiguration.Required {

	@Configuration
	@Import({ DataExtensionTestConfiguration.class, DataExtensionConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

	}
}