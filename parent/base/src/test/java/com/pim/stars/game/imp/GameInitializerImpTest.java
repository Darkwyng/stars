package com.pim.stars.game.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.game.GameTestConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.game.api.effects.GameInitializationPolicy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GameInitializerImpTest.TestConfiguration.class)
public class GameInitializerImpTest {

	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private GameInitializationPolicy gameInitializationPolicy;
	@Autowired
	private DataExtender dataExtender;

	private static int gameInitializationPolicyCallCounter = 0;

	@BeforeEach
	public void setUp() {
		gameInitializationPolicyCallCounter = 0;
	}

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(gameInitializer, not(nullValue()));
		assertThat(gameInitializationPolicy, not(nullValue()));
	}

	@Test
	public void testThatNewGameInitializationDataIsCreated() {
		assertThat(gameInitializer.createNewGameInitializationData(), not(nullValue()));
	}

	@Test
	public void testThatPoliciesAreCalledDuringGameInitialization() {
		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		assertThat(gameInitializationPolicyCallCounter, is(0));
		final Game game = gameInitializer.initializeGame(initializationData);

		// Check gameInitializationPolicy was called:
		assertThat(gameInitializationPolicyCallCounter, is(1));

		// Check dataExtender was called:
		verify(dataExtender).extendData(eq(game));

	}

	@Configuration
	protected static class TestConfiguration extends GameTestConfiguration {

		@Bean
		public GameInitializationPolicy gameInitializationPolicy() {
			return new GameInitializationPolicy() {

				@Override
				public void initializeGame(final Game game, final GameInitializationData initializationData) {
					gameInitializationPolicyCallCounter++;
				}
			};
		}
	}
}
