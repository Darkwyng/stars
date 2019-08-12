package com.pim.stars.race.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.race.RaceTestConfiguration;
import com.pim.stars.race.api.extensions.GameRaceCollection;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RaceTestConfiguration.class)
public class RaceConfigurationTest {

	@Autowired
	private GameRaceCollection gameRaceCollection;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(gameRaceCollection, not(nullValue()));
		new RaceConfiguration.Complete(); // (for test coverage)
	}
}