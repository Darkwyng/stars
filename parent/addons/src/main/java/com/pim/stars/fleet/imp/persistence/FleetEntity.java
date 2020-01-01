package com.pim.stars.fleet.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class FleetEntity {

	@Id
	private FleetEntityId entityId;
	private String ownerId;

	private Collection<FleetComponentEntity> components = new ArrayList<>();
}

@Getter
@Setter
class FleetComponentEntity {

	private String shipDesignId;
	private int numberOfShips;
}

@Getter
@Setter
class FleetEntityId {

	private String id;
	private String gameId;
	private int year;
}