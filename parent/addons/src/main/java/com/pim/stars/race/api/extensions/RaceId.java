package com.pim.stars.race.api.extensions;

import org.springframework.stereotype.Component;

import com.pim.stars.id.api.extensions.IdDataExtensionPolicy;
import com.pim.stars.turn.api.Race;

@Component
public class RaceId extends IdDataExtensionPolicy<Race> {

	@Override
	public Class<Race> getEntityClass() {
		return Race.class;
	}
}