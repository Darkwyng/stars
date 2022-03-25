package com.pim.stars.game.imp;

import com.pim.stars.game.api.Game;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class GameImp implements Game {

	private final String gameId;
	private final int year;

	@Override
	public String getId() {
		return gameId;
	}

	@Override
	public int getYear() {
		return year;
	}
}
