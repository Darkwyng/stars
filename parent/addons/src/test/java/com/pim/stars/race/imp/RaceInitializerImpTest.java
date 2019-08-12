package com.pim.stars.race.imp;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.race.RaceTestConfiguration;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.RaceInitializer;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RaceTestConfiguration.class)
public class RaceInitializerImpTest {

	@Autowired
	private RaceInitializer raceInitializer;

	@Autowired
	private DataExtender dataExtender;

	@Test
	public void testThatRaceIsInitialized() {
		final Race race = raceInitializer.initializeRace();
		assertThat(race, not(nullValue()));
		verify(dataExtender).extendData(race, Race.class);
	}
}