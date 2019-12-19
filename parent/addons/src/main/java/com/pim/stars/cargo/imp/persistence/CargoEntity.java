package com.pim.stars.cargo.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
class CargoEntity {

	@Id
	private String entityId;

	private String gameId;
	private int year;

	private Collection<CargoEntityItem> items = new ArrayList<>();

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(final String entityId) {
		this.entityId = entityId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(final String gameId) {
		this.gameId = gameId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
		this.year = year;
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