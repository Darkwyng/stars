package com.pim.stars.colonization.imp.effects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.pim.stars.planets.api.Planet;

public class HomeworldGameInitializationPolicyTest {

	@Mock
	private Random random;

	@InjectMocks
	private HomeworldGameInitializationPolicy testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(random.nextInt(anyInt())).thenReturn(0, 0, 1);

		ReflectionTestUtils.setField(testee, "random", random);
	}

	@Test
	public void testNotEnoughPlanetsIsRejected() {
		final Collection<Planet> planets = Arrays.asList();
		final Collection<Planet> homeworlds = Arrays.asList();
		assertThrows(IllegalArgumentException.class, () -> testee.selectNewHomeworld(planets, homeworlds));
	}

	@Test
	public void testThatRandomChoiceIsRepeated() {
		final Planet zero = mock(Planet.class);
		final Planet one = mock(Planet.class);
		final Collection<Planet> planets = Arrays.asList(zero, one);
		final Collection<Planet> homeworlds = new ArrayList<>(Arrays.asList(zero));

		final Planet homeworld = testee.selectNewHomeworld(planets, homeworlds);
		verify(random, times(3)).nextInt(2);
		assertThat(homeworld, is(one));
		assertThat(homeworlds, hasItems(zero, one));
	}

}
