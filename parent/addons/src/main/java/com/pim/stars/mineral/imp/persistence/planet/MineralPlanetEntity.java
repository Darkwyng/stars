package com.pim.stars.mineral.imp.persistence.planet;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MineralPlanetEntity {

	@Id
	private PlanetEntityId entityId = new PlanetEntityId();

	private boolean isHomeworld;
	private int mineCount;
	private Collection<MineralTypeWithQuantity> fractionalMinedQuantities = new ArrayList<>();
	private Collection<MineralTypeWithQuantity> mineralConcentrations = new ArrayList<>();

	public MineralPlanetEntity() {
		super();
	}

	public PlanetEntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(final PlanetEntityId entityId) {
		this.entityId = entityId;
	}

	public boolean isHomeworld() {
		return isHomeworld;
	}

	public void setHomeworld(final boolean isHomeworld) {
		this.isHomeworld = isHomeworld;
	}

	public int getMineCount() {
		return mineCount;
	}

	public void setMineCount(final int mineCount) {
		this.mineCount = mineCount;
	}

	public Collection<MineralTypeWithQuantity> getFractionalMinedQuantities() {
		return fractionalMinedQuantities;
	}

	public void setFractionalMinedQuantities(final Collection<MineralTypeWithQuantity> fractionalMinedQuantities) {
		this.fractionalMinedQuantities = fractionalMinedQuantities;
	}

	public Collection<MineralTypeWithQuantity> getMineralConcentrations() {
		return mineralConcentrations;
	}

	public void setMineralConcentrations(final Collection<MineralTypeWithQuantity> mineralConcentrations) {
		this.mineralConcentrations = mineralConcentrations;
	}

	public static class MineralTypeWithQuantity {

		private String mineralTypeId;
		private double quantity;

		public MineralTypeWithQuantity() {
			super();
		}

		public MineralTypeWithQuantity(final String mineralTypeId, final double quantity) {
			super();
			this.mineralTypeId = mineralTypeId;
			this.quantity = quantity;
		}

		public String getMineralTypeId() {
			return mineralTypeId;
		}

		public void setMineralTypeId(final String mineralTypeId) {
			this.mineralTypeId = mineralTypeId;
		}

		public double getQuantity() {
			return quantity;
		}

		public void setQuantity(final double quantity) {
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "MineralTypeWithQuantity [mineralTypeId=" + mineralTypeId + ", quantity=" + quantity + "]";
		}
	}

	public static class PlanetEntityId {

		private String gameId;
		private int year;
		private String name;

		public PlanetEntityId(final String gameId, final int year, final String name) {
			this.gameId = gameId;
			this.year = year;
			this.name = name;
		}

		public PlanetEntityId() {
			this(null, 0, null);
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

		public String getName() {
			return name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "PlanetEntityId [gameId=" + gameId + ", year=" + year + ", name=" + name + "]";
		}
	}
}
