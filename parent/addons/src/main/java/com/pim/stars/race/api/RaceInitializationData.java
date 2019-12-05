package com.pim.stars.race.api;

import com.pim.stars.dataextension.api.Entity;

public interface RaceInitializationData extends Entity<RaceInitializationData> {

	@Override
	default Class<RaceInitializationData> getEntityClass() {
		return RaceInitializationData.class;
	}
}
