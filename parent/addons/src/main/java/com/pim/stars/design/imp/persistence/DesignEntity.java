package com.pim.stars.design.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class DesignEntity {

	@Id
	private DesignEntityId entityId;
	private String ownerId;
	private String designTypeId;

	private String name;
	private String status;

	private String hullId;
	private Collection<FilledGadgetSlot> filledGadgetSlots = new ArrayList<>();
}

@Getter
@Setter
class FilledGadgetSlot {

	private String slotId;
	private String gadgetId;
	private int numberOfGadgets;
}

@Getter
@Setter
class DesignEntityId {

	private String id;
	private String gameId;
	private int year;
}

enum DesignEntityStatus {
	EDITABLE, ACTIVE, DELETED;
}