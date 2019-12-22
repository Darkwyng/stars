package com.pim.stars.planets.imp.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class PlanetEntity {

	@Id
	private PlanetEntityId entityId = new PlanetEntityId();
	private String ownerId;

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlanetEntityId {

		private String gameId;
		private int year;
		private String name;
	}
}