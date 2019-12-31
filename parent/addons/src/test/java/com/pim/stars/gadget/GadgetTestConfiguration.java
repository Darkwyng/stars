package com.pim.stars.gadget;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.spring.SpringConfiguration;

public class GadgetTestConfiguration {

	@Configuration
	@Import({ GadgetTestConfiguration.class, GadgetConfiguration.Provided.class, // 
			SpringConfiguration.Provided.class // These two modules are tested together here 
	})
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

	}
}
