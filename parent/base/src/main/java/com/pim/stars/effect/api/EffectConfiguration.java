package com.pim.stars.effect.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.imp.EffectCalculatorImp;
import com.pim.stars.effect.imp.EffectExecutorImp;
import com.pim.stars.effect.imp.EffectProviderImp;
import com.pim.stars.effect.imp.policies.DefaultEffectHolderAndEffectProviderPolicy;

public interface EffectConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public EffectProvider effectProvider() {
			return new EffectProviderImp();
		}

		@Bean
		public EffectCalculator EffectCalculator() {
			return new EffectCalculatorImp();
		}

		@Bean
		public EffectExecutor effectExecutor() {
			return new EffectExecutorImp();
		}

		@Bean
		public DefaultEffectHolderAndEffectProviderPolicy defaultEffectHolderAndEffectProviderPolicy() {
			return new DefaultEffectHolderAndEffectProviderPolicy();
		}
	}

	@Configuration
	@Import({}) // Currently nothing is imported by this component
	public static class Complete extends Provided {

	}

	public static interface Required {
		// Currently nothing is required by this component
	}
}
