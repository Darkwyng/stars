package com.pim.stars.race.imp;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.RaceInitializer;

public class RaceInitializerImp implements RaceInitializer {

	@Autowired
	private DataExtender dataExtender;

	@Override
	public Race initializeRace() {
		final Race race = new RaceImp();

		dataExtender.extendData(race, Race.class);

		return race;
	}
}
