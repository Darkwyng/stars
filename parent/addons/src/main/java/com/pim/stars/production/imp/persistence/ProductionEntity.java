package com.pim.stars.production.imp.persistence;

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
public class ProductionEntity {

	@Id
	private PlanetEntityId entityId = new PlanetEntityId();
	private Collection<ProductionEntityItem> items = new ArrayList<>();
}

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
class PlanetEntityId {

	private String gameId;
	private int year;
	private String planetName;
}

@Getter
@Setter
@ToString
class ProductionEntityItem {

	private String itemTypeId;
	private int numberOfItemsToBuild;

	private Collection<ProductionEntityItemInvestedCost> investedCosts = new ArrayList<>();
}

@Getter
@Setter
@ToString
class ProductionEntityItemInvestedCost {

	private String costTypeId;
	private int amount;
}