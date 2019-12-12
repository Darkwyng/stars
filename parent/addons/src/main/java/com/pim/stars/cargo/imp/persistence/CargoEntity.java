package com.pim.stars.cargo.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
class CargoEntity {

	@Id
	private String entityId;
	private Collection<CargoEntityItem> items = new ArrayList<>();

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(final String entityId) {
		this.entityId = entityId;
	}

	public Collection<CargoEntityItem> getItems() {
		return items;
	}

	public void setItems(final Collection<CargoEntityItem> items) {
		this.items = items;
	}
}

class CargoEntityItem {

	private String typeId;
	private int quantity;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(final String type) {
		this.typeId = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}
}