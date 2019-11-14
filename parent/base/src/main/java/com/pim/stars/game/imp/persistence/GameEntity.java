package com.pim.stars.game.imp.persistence;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GameEntity {

	private String gameId;
	private int year;
	private boolean isLatest;

	public String getGameId() {
		return gameId;
	}

	public void setGameId(final String gameId) {
		this.gameId = gameId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public boolean isLatest() {
		return isLatest;
	}

	public void setLatest(final boolean isLatest) {
		this.isLatest = isLatest;
	}
}
