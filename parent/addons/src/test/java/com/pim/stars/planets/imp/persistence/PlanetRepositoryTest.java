package com.pim.stars.planets.imp.persistence;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlanetRepositoryTest {

	@Mock // Mocked, because we are testing default methods of an interface
	private PlanetRepository testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(testee.findByGameIdAndYearAndName(any(), anyInt(), any())).thenAnswer(Answers.CALLS_REAL_METHODS);
	}

	@Test
	public void test() {
		when(testee.findById(any())).thenReturn(Optional.empty());
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> testee.findByGameIdAndYearAndName("gameId", 17, "planetName"));
		assertThat(exception.getMessage(),
				allOf(containsString("gameId"), containsString("17"), containsString("planetName")));
	}
}