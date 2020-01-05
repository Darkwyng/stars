package com.pim.stars.location.imp.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationEntity {

	protected final static String ENTITY_ID = "_id";
	protected final static String COORDINATES = "coordinates";

	@Id
	private LocationEntityId entityId;
	private LocationEntityCoordinates coordinates;
}

@Getter
@Setter
@EqualsAndHashCode // used when grouping by coordinates
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