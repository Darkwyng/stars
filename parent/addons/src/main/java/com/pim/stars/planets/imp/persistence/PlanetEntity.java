package com.pim.stars.planets.imp.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlanetEntity {

	@Id
	private PlanetEntityId entityId = new PlanetEntityId();

	private String ownerId;

	public PlanetEntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(final PlanetEntityId entityId) {
		this.entityId = entityId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(final String ownerId) {
		this.ownerId = ownerId;
	}

	public static class PlanetEntityId {

		private String gameId;
		private int year;
		private String name;

		public PlanetEntityId(final String gameId, final int year, final String name) {
			this.gameId = gameId;
			this.year = year;
			this.name = name;
		}

		public PlanetEntityId() {
			this(null, 0, null);
		}

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

		@Override
		public String toString() {
			return "PlanetEntityId [gameId=" + gameId + ", year=" + year + ", name=" + name + "]";
		}
	}
}