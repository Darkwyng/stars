package com.pim.stars.turn.api;

import com.pim.stars.dataextension.api.Entity;

public interface Race extends Entity<Race> {

	@Override
	public default Class<Race> getEntityClass() {
		return Race.class;
	}
}
