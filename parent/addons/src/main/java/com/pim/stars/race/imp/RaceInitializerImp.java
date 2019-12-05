package com.pim.stars.race.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.RaceInitializer;

@Component
public class RaceInitializerImp implements RaceInitializer {

	@Autowired
	private DataExtender dataExtender;

	@Override
	public RaceInitializationData initializeRace() {
		return dataExtender.extendData(new RaceInitializationDataImp());
	}

}
