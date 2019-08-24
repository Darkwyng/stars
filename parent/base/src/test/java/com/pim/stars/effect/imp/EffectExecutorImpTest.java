package com.pim.stars.effect.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.effect.EffectTestConfiguration;
import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.effect.api.EffectExecutor.EffectContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EffectExecutorImpTest.TestConfiguration.class)
public class EffectExecutorImpTest {

	@Autowired
	private EffectExecutor effectExecutor;

	@Test
	public void testThatEffectsCanBeExecuted() {
		effectExecutor.executeEffect(null, TestEffect.class, null, (policy, context) -> {
			assertThat(context, not(nullValue()));
			policy.execute(new Object(), context);
		});
	}

	private interface TestEffect extends Effect {

		public void execute(Object input, EffectContext context);
	}

	protected static class FirstTestEffect implements TestEffect {

		@Override
		public int getSequence() {
			return 1;
		}

		@Override
		public void execute(final Object input, final EffectContext context) {
			assertThat(context.get("myKey"), is(nullValue()));
			context.set("myKey", 17);
		}
	}

	protected static class SecondTestEffect implements TestEffect {

		@Override
		public int getSequence() {
			return 2;
		}

		@Override
		public void execute(final Object input, final EffectContext context) {
			assertThat(context.get("myKey"), is(17));
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
	}
}