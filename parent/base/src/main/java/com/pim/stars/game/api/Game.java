package com.pim.stars.game.api;

import com.pim.stars.dataextension.api.Entity;

public interface Game extends Entity<Game> {

	@Override
	public default Class<Game> getEntityClass() {
		return Game.class;
	}

	public String getId();

	public int getYear();
}
