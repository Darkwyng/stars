package com.pim.stars.game.api;

import com.pim.stars.dataextension.api.Entity;

public interface GameInitializationData extends Entity<GameInitializationData> {

	@Override
	public default Class<GameInitializationData> getEntityClass() {
		return GameInitializationData.class;
	}
}
