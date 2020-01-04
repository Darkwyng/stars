package com.pim.stars.location.imp.persistence;

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
public class LocationEntity {

	protected final static String ENTITY_ID = "entityId";
	protected final static String COORDINATES = "coordinates";

	private LocationEntityId entityId;
	private LocationEntityCoordinates coordinates;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class LocationEntityCoordinates {

	private int x;
	private int y;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class LocationEntityId {

	private String gameId;
	private String locationHolderType;
	private String locationHolderId;
}