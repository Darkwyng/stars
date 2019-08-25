package com.pim.stars.resource;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectCalculator;

@Configuration
@Import({ ResourceConfiguration.Provided.class })
public class ResourceTestConfiguration implements ResourceConfiguration.Required {

	@Override
	@Bean
	public EffectCalculator effectCalculator() {
		return mock(EffectCalculator.class);
	}
}