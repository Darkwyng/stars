package com.pim.stars.game.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.DataExtensionConfiguration;
import com.pim.stars.effect.api.EffectConfiguration;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.game.imp.GameGeneratorImp;
import com.pim.stars.game.imp.GameInitializerImp;

public interface GameConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public GameInitializer gameInitializer() {
			return new GameInitializerImp();
		}

		@Bean
		public GameGenerator gameGenerator() {
			return new GameGeneratorImp();
		}
	}

	@Configuration
	@Import({ DataExtensionConfiguration.Complete.class, EffectConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public DataExtender dataExtender();

		public EffectProvider effectProvider();
	}
}
