package com.pim.stars.effect.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.effect.EffectTestConfiguration;
import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectCalculator.EffectContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EffectCalculatorImpTest.TestConfiguration.class)
public class EffectCalculatorImpTest {

	@Autowired
	private EffectCalculator effectCalculator;

	@Test
	public void testThatEffectsCanBeCalculated() {
		final Double result = effectCalculator.calculateEffect(null, TestEffect.class, null, 2.0,
				(policy, context, currentValue) -> {
					assertThat(context, not(nullValue()));
					return policy.calculate(new Object(), context, currentValue);
				});
		assertThat(result, is(25.0));
	}

	private interface TestEffect extends Effect {

		public double calculate(Object input, EffectContext context, double currentValue);
	}

	protected static class FirstTestEffect implements TestEffect {

		@Override
		public int getSequence() {
			return 1;
		}

		@Override
		public double calculate(final Object input, final EffectContext context, final double currentValue) {
			assertThat(context.get("myKey"), is(nullValue()));
			context.set("myKey", 17);
			return Math.pow(currentValue, 2);
		}
	}

	protected static class SecondTestEffect implements TestEffect {

		@Override
		public int getSequence() {
			return 2;
		}

		@Override
		public double calculate(final Object input, final EffectContext context, final double currentValue) {
			assertThat(context.get("myKey"), is(17));
			return Math.pow(currentValue + 1, 2);
		}

		@Override
		public Collection<Class<? extends Effect>> getDeactivatedEffects() {
			return Collections.singleton(DeactivatedTestEffect.class);
		}
	}

	protected static class DeactivatedTestEffect implements TestEffect {

		@Override
		public int getSequence() {
			return 1;
		}

		@Override
		public double calculate(final Object input, final EffectContext context, final double currentValue) {
			throw new AssertionError("This should never be called, because this effect is deactivated.");
		}
	}

	@Configuration
	protected static class TestConfiguration extends EffectTestConfiguration {

		@Bean
		public FirstTestEffect firstTestEffect() {
			return new FirstTestEffect();
		}

		@Bean
		public SecondTestEffect secondTestEffect() {
			return new SecondTestEffect();
		}

		@Bean
		public DeactivatedTestEffect deactivatedTestEffect() {
			return new DeactivatedTestEffect();
		}
	}
}