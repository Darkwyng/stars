package com.pim.stars.resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectConfiguration;

public interface ResourceConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

	}

	@Configuration
	@Import({ EffectConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public EffectCalculator effectCalculator();
	}
}
