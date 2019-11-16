package com.pim.stars.mineral.imp.persistence;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MineralRaceEntity {

	private String raceId;
	private int mineProductionCost;
	private double mineEfficiency;

	public MineralRaceEntity() {
		super();
	}

	public String getRaceId() {
		return raceId;
	}

	public void setRaceId(final String raceId) {
		this.raceId = raceId;
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

}
