package com.pim.stars.turn.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.turn.TurnConfiguration;
import com.pim.stars.turn.TurnTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TurnTestConfiguration.class)
public class TurnConfigurationTest {

	@Autowired
	private TurnCreator turnCreator;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(turnCreator, not(nullValue()));
		new TurnConfiguration.Complete(); // (for test coverage)
	}
}
