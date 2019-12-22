package com.pim.stars.mineral.imp.persistence.race;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MineralRaceEntity {

	@Id
	private RaceEntityId entityId = new RaceEntityId();

	private int mineProductionCost;
	private double mineEfficiency;

	public MineralRaceEntity() {
		super();
	}

	public int getMineProductionCost() {
		return mineProductionCost;
	}

	public void setMineProductionCost(final int mineProductionCost) {
		this.mineProductionCost = mineProductionCost;
	}

	public double getMineEfficiency() {
		return mineEfficiency;
	}

	public void setMineEfficiency(final double mineEfficiency) {
		this.mineEfficiency = mineEfficiency;
	}

	public RaceEntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(final RaceEntityId entityId) {
		this.entityId = entityId;
	}

	public static class RaceEntityId {

		private String gameId;
		private String raceId;

		public String getGameId() {
			return gameId;
		}

		public void setGameId(final String gameId) {
			this.gameId = gameId;
		}

		public String getRaceId() {
			return raceId;
		}

		public void setRaceId(final String raceId) {
			this.raceId = raceId;
		}
	}

}
