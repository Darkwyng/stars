package com.pim.stars.game.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.GameTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GameTestConfiguration.class)
public class GameConfigurationTest {

	@Autowired
	private GameInitializer gameInitializer;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(gameInitializer, not(nullValue()));
		new GameConfiguration.Complete(); // (for test coverage)
	}
}
