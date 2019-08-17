package com.pim.stars.turn.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.turn.TurnTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TurnCreatorImpTest.TestConfiguration.class)
public class TurnCreatorImpTest {

	@Test
	public void testThat() {

	}

	@Configuration
	protected static class TestConfiguration extends TurnTestConfiguration {

	}
}