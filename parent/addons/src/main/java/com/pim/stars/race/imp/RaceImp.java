package com.pim.stars.race.imp;

import com.pim.stars.race.api.Race;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class RaceImp implements Race {

	private final String raceId;

	@Override
	public String getId() {
		return raceId;
	}
}
