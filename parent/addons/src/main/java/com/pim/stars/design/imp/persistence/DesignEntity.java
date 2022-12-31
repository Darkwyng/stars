package com.pim.stars.design.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class DesignEntity {

	@Id
	private DesignEntityId entityId;
	private String ownerId;

	private String name;
	private String status;

	private String hullId;
	private String hullTypeId;
	private Collection<FilledGadgetSlot> filledGadgetSlots = new ArrayList<>();

	@Getter
	@AllArgsConstructor
	public static class FilledGadgetSlot {

		private final String slotId;
		private final String gadgetId;
		private final int numberOfGadgets;
	}

	@Getter
	@AllArgsConstructor
	public static class DesignEntityId {

		private final String id;
		private final String gameId;
		private final int year;
	}

	public static enum DesignEntityStatus {

		EDITABLE, ACTIVE, DELETED;

		public String getId() {
			return name();
		}
	}
}