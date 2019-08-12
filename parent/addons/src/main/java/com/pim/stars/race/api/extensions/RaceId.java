package com.pim.stars.race.api.extensions;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.id.api.extensions.IdDataExtensionPolicy;
import com.pim.stars.race.api.Race;

public class RaceId extends IdDataExtensionPolicy {

	@Override
	public Class<? extends Entity> getEntityClass() {
		return Race.class;
	}
}