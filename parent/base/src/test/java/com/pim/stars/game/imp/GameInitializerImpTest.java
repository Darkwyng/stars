package com.pim.stars.game.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.game.GameTestConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.game.api.GameProvider;
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
	@Autowired
	private EffectProvider effectProvider;
	@Autowired
	private GameProvider gameProvider;

	private static int gameInitializationPolicyCallCounter = 0;

	@BeforeEach
	public void setUp() {
		gameInitializationPolicyCallCounter = 0;
		when(effectProvider.getEffectCollection(Mockito.any(), Mockito.any(), eq(GameInitializationPolicy.class)))
				.thenReturn(Arrays.asList(gameInitializationPolicy));
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

	@Test
	public void testThatGameIsPersistedAfterInitialization() {
		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		assertThat(gameProvider.getAllGames().collect(Collectors.toList()), empty());
		final Game game = gameInitializer.initializeGame(initializationData);
		assertThat(gameProvider.getAllGames().collect(Collectors.toList()), not(empty()));

		final Optional<Game> optional = gameProvider.getGameWithLatestYearById(game.getId());
		assertThat(optional.isPresent(), is(true));
		assertThat(optional.get().getId(), is(game.getId()));
		assertThat(optional.get().getYear(), is(2500));
	}

	@Configuration
	@Import({ GameTestConfiguration.class })
	protected static class TestConfiguration {

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
