/**
 *
 */
package com.pim.stars.game.imp.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class GameEntityTest {

	@Test
	public void testEntityGettersReturnCorrectData() {
		final GameEntity entity = new GameEntity();
		entity.setGameId("47");
		entity.setYear(48);
		entity.setLatest(true);

		assertAll(() -> assertThat(entity.getGameId(), is("47")), //
				() -> assertThat(entity.getYear(), is(48)), //
				() -> assertThat(entity.isLatest(), is(true)));
	}

}
