package com.pim.stars.effect;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import({ EffectConfiguration.Provided.class })
public class EffectTestConfiguration implements EffectConfiguration.Required {

	@Configuration
	@Import({ EffectTestConfiguration.class, EffectConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

	}
}