package com.pim.stars.mineral.imp.persistence.planet;

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
public class MineralPlanetEntity {

	@Id
	private PlanetEntityId entityId = new PlanetEntityId();

	private boolean isHomeworld;
	private int mineCount;
	private Collection<MineralTypeWithQuantity> fractionalMinedQuantities = new ArrayList<>();
	private Collection<MineralTypeWithQuantity> mineralConcentrations = new ArrayList<>();
}

@Getter
@Setter
@AllArgsConstructor
@ToString
class MineralTypeWithQuantity {

	private String mineralTypeId;
	private double quantity;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class PlanetEntityId {

	private String gameId;
	private int year;
	private String name;
}