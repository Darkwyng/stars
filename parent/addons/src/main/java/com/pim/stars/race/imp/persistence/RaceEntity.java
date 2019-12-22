package com.pim.stars.race.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class RaceEntity {

	@Id
	private RaceEntityId entityId = new RaceEntityId();

	private String primaryRacialTraitId;
	private Collection<String> secondaryRacialTraitIds = new ArrayList<>();

	@Getter
	@Setter
	@ToString
	public static class RaceEntityId {

		private String gameId;
		private String raceId;
	}
}
