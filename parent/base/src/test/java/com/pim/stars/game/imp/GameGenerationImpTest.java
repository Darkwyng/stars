package com.pim.stars.game.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.effect.api.EffectExecutor.EffectFunction;
import com.pim.stars.game.GameTestConfiguration;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.effects.GameGenerationPolicy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GameGenerationImpTest.TestConfiguration.class)
@ActiveProfiles("WithoutPersistence")
public class GameGenerationImpTest {

	@Autowired
	private GameGenerator gameGenerator;
	@Autowired
	private GameGenerationPolicy gameGenerationPolicy;
	@Autowired
	private EffectExecutor effectExecutor;

	private static int gameGenerationPolicyCallCounter = 0;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setUp() {
		gameGenerationPolicyCallCounter = 0;
		doAnswer(inv -> {
			final EffectFunction<GameGenerationPolicy> function = inv.getArgument(3, EffectFunction.class);
			function.execute(gameGenerationPolicy, null);
			return null;
		}).when(effectExecutor).executeEffect(any(), eq(GameGenerationPolicy.class), eq(null), any());
	}

	@Test
	public void testThatPoliciesAreCalledDuringGameGenerator() {
		assertThat(gameGenerationPolicy, not(nullValue()));
		assertThat(gameGenerationPolicyCallCounter, is(0));
		gameGenerator.generateGame(new GameImp("myId", 2507));

		// Check gameInitializationPolicy was called:
		assertThat(gameGenerationPolicyCallCounter, is(1));
	}

	@Configuration
	@Import({ GameTestConfiguration.WithoutPersistence.class })
	protected static class TestConfiguration {

		@Bean
		public GameGenerationPolicy gameGenerationPolicy() {
			return new GameGenerationPolicy() {

				@Override
				public void generateGame(final GameGenerationContext generationContext) {
					generationContext.set("TheTestKey", "TheTestValue");
					assertThat(generationContext.get("TheTestKey"), is("TheTestValue"));

					gameGenerationPolicyCallCounter++;
				}
			};
		}
	}
}
