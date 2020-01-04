package com.pim.stars.race.imp.persistence;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.Game;

public class RaceProviderImpTest {

	@Mock
	private RaceRepository raceRepository;

	@InjectMocks
	private RaceProviderImp testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() {
		when(raceRepository.findByGameId(Mockito.any())).thenReturn(Collections.emptyList());
		assertThrows(IllegalArgumentException.class, () -> testee.getRacesByGame(mock(Game.class)));
	}
}
