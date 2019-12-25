package com.pim.stars.race.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.race.RaceTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RaceTestConfiguration.WithoutPersistence.class })
@ActiveProfiles("WithoutPersistence")
public class RaceConfigurationTest {

	@Autowired
	private RaceProvider raceProvider;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(raceProvider, not(nullValue()));
	}

}