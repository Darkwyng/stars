package com.pim.stars.planets.imp.persistence;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlanetEntity {

	private String gameId;
	private int year;
	private String name;
	private String ownerId;

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

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(final String ownerId) {
		this.ownerId = ownerId;
	}

}