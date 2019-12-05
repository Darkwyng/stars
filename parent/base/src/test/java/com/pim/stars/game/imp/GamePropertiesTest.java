package com.pim.stars.game.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class GamePropertiesTest {

	@Test
	public void testGettersReturnCorrectData() {
		final GameProperties testee = new GameProperties();
		assertThat(testee.getStartingYear(), is(2500));

		testee.setStartingYear(2501);
		assertThat(testee.getStartingYear(), is(2501));
	}

}
