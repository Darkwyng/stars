package com.pim.stars.planets.api;

import com.pim.stars.dataextension.api.Entity;

public interface Planet extends Entity<Planet> {

	@Override
	public default Class<Planet> getEntityClass() {
		return Planet.class;
	}
}
