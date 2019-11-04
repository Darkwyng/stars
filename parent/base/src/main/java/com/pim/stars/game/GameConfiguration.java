package com.pim.stars.game;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.DataExtensionConfiguration;
import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.EffectConfiguration;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.id.IdConfiguration;
import com.pim.stars.id.api.IdCreator;

public interface GameConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

	}

	@Configuration
	@Import({ DataExtensionConfiguration.Complete.class, EffectConfiguration.Complete.class,
			IdConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public DataExtender dataExtender();

		public EffectProvider effectProvider();

		public IdCreator idCreator();
	}
}
