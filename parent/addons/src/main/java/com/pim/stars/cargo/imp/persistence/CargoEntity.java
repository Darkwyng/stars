package com.pim.stars.cargo.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
class CargoEntity {

	@Id
	private CargoEntityId entityId = new CargoEntityId();

	private Collection<CargoEntityItem> items = new ArrayList<>();

	public CargoEntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(final CargoEntityId entityId) {
		this.entityId = entityId;
	}

	public Collection<CargoEntityItem> getItems() {
		return items;
	}

	public void setItems(final Collection<CargoEntityItem> items) {
		this.items = items;
	}

}

class CargoEntityId {

	private String gameId;
	private int year;
	private String cargoHolderType;
	private String cargoHolderId;

	public CargoEntityId() {
	}

	public CargoEntityId(final String gameId, final int year, final String cargoHolderType,
			final String cargoHolderId) {
		this.gameId = gameId;
		this.year = year;
		this.cargoHolderType = cargoHolderType;
		this.cargoHolderId = cargoHolderId;
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

	public String getCargoHolderType() {
		return cargoHolderType;
	}

	public void setCargoHolderType(final String cargoHolderType) {
		this.cargoHolderType = cargoHolderType;
	}

	public String getCargoHolderId() {
		return cargoHolderId;
	}

	public void setCargoHolderId(final String cargoHolderId) {
		this.cargoHolderId = cargoHolderId;
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