package com.pim.stars.cargo.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

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
class CargoEntity {

	@Id
	private CargoEntityId entityId = new CargoEntityId();
	private Collection<CargoEntityItem> items = new ArrayList<>();
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class CargoEntityId {

	private String gameId;
	private int year;
	private String cargoHolderType;
	private String cargoHolderId;
}

@Getter
@Setter
@ToString
class CargoEntityItem {

	private String typeId;
	private int quantity;
}