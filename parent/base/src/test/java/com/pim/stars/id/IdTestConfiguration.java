package com.pim.stars.id;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

public class IdTestConfiguration implements IdConfiguration.Required {

	@Configuration
	@Import({ IdTestConfiguration.class, IdConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

	}
}