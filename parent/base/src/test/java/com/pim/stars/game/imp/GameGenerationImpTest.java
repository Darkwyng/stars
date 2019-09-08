package com.pim.stars.game.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.game.GameTestConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.effects.GameGenerationPolicy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GameGenerationImpTest.TestConfiguration.class)
public class GameGenerationImpTest {

	@Autowired
	private GameGenerator gameGenerator;
	@Autowired
	private GameGenerationPolicy gameGenerationPolicy;
	@Autowired
	private EffectProvider effectProvider;

	private static int gameGenerationPolicyCallCounter = 0;

	@BeforeEach
	public void setUp() {
		gameGenerationPolicyCallCounter = 0;
		when(effectProvider.getEffectCollection(Mockito.any(), Mockito.any(), eq(GameGenerationPolicy.class)))
				.thenReturn(Arrays.asList(gameGenerationPolicy));
	}

	@Test
	public void testThatPoliciesAreCalledDuringGameGenerator() {
		assertThat(gameGenerationPolicy, not(nullValue()));
		assertThat(gameGenerationPolicyCallCounter, is(0));
		gameGenerator.generateGame(new GameImp());

		// Check gameInitializationPolicy was called:
		assertThat(gameGenerationPolicyCallCounter, is(1));
	}

	@Configuration
	protected static class TestConfiguration extends GameTestConfiguration {

		@Bean
		public GameGenerationPolicy gameGenerationPolicy() {
			return new GameGenerationPolicy() {

				@Override
				public void generateGame(final Game game, final GameGenerationContext generationContext) {
					gameGenerationPolicyCallCounter++;
				}
			};
		}
	}
}
