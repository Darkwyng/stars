package com.pim.stars.race.imp;

import com.pim.stars.race.api.Race;

public class RaceImp implements Race {

	private final String raceId;

	public RaceImp(final String raceId) {
		this.raceId = raceId;
	}

	@Override
	public String getId() {
		return raceId;
	}
}
