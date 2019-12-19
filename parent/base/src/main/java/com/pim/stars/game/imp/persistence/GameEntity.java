package com.pim.stars.game.imp.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GameEntity {

	@Id
	private GameEntityId entityId = new GameEntityId();
	private boolean isLatest;

	public boolean isLatest() {
		return isLatest;
	}

	public void setLatest(final boolean isLatest) {
		this.isLatest = isLatest;
	}

	public GameEntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(final GameEntityId entityId) {
		this.entityId = entityId;
	}

	public static class GameEntityId {

		private String gameId;
		private int year;

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
	}
}
