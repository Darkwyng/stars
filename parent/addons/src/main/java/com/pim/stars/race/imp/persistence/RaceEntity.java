package com.pim.stars.race.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RaceEntity {

	private String gameId;
	private String raceId;

	private String primaryRacialTraitId;
	private Collection<String> secondaryRacialTraitIds;

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

}
