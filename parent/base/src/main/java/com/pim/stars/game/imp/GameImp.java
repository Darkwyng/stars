package com.pim.stars.game.imp;

import com.pim.stars.game.api.Game;

public class GameImp implements Game {

	private final String gameId;
	private final int year;

	public GameImp(final String gameId, final int year) {
		this.gameId = gameId;
		this.year = year;
	}

	@Override
	public String getId() {
		return gameId;
	}

	@Override
	public int getYear() {
		return year;
	}
}
