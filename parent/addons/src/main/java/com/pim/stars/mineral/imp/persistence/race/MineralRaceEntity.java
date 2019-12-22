package com.pim.stars.mineral.imp.persistence.race;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class MineralRaceEntity {

	@Id
	private RaceEntityId entityId = new RaceEntityId();
	private int mineProductionCost;
	private double mineEfficiency;

	@Getter
	@Setter
	@ToString
	public class RaceEntityId {

		private String gameId;
		private String raceId;
	}
}
