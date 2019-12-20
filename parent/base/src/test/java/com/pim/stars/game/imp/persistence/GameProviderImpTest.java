package com.pim.stars.game.imp.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.Game;

public class GameProviderImpTest {

	@Mock
	private GameRepository gameRepository;

	private final GameEntity gameOne = new GameEntity();
	private final GameEntity gameTwo = new GameEntity();

	@InjectMocks
	private GameProviderImp testee;

	@BeforeEach
	private void setUp() {
		MockitoAnnotations.initMocks(this);
		when(gameRepository.findByGameIdAndIsLatest(any(), anyBoolean())).thenReturn(Arrays.asList(gameOne, gameTwo));
	}

	@Test
	public void test() {
		gameOne.getEntityId().setGameId("47");
		gameOne.getEntityId().setYear(2501);

		gameTwo.getEntityId().setGameId("48");
		gameTwo.getEntityId().setYear(2502);

		final Optional<Game> optional = testee.getGameWithLatestYearById("49");
		assertThat(optional.isPresent(), is(true));
		assertThat(optional.get().getId(), is("48"));
		assertThat(optional.get().getYear(), is(2502));
	}

}
