package com.pim.stars.race.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RaceEntity {

	@Id
	private RaceEntityId entityId = new RaceEntityId();

	private String primaryRacialTraitId;
	private Collection<String> secondaryRacialTraitIds = new ArrayList<>();

	public RaceEntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(final RaceEntityId entityId) {
		this.entityId = entityId;
	}

	public String getPrimaryRacialTraitId() {
		return primaryRacialTraitId;
	}

	public void setPrimaryRacialTraitId(final String primaryRacialTraitId) {
		this.primaryRacialTraitId = primaryRacialTraitId;
	}

	public Collection<String> getSecondaryRacialTraitIds() {
		return secondaryRacialTraitIds;
	}

	public void setSecondaryRacialTraitIds(final Collection<String> secondaryRacialTraitIds) {
		this.secondaryRacialTraitIds = secondaryRacialTraitIds;
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
