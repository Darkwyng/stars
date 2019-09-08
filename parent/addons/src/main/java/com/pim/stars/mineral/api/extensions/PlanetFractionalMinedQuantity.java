package com.pim.stars.mineral.api.extensions;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.mineral.api.extensions.PlanetFractionalMinedQuantity.FractionalMinedQuantities;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.planets.api.Planet;

@Component
public class PlanetFractionalMinedQuantity implements DataExtensionPolicy<Planet, FractionalMinedQuantities> {

	@Autowired(required = false)
	private List<MineralType> mineralTypes;

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Optional<? extends FractionalMinedQuantities> getDefaultValue() {
		return Optional.of(new FractionalMinedQuantities(mineralTypes));
	}

	public class FractionalMinedQuantities {

		private final Collection<FractionalMinedQuantity> items;

		public FractionalMinedQuantities(final List<MineralType> mineralTypes) {
			items = mineralTypes.stream().map(type -> new FractionalMinedQuantity(type, 0))
					.collect(Collectors.toList());
		}

		public Collection<FractionalMinedQuantity> getItems() {
			return items;
		}
	}

	public class FractionalMinedQuantity {

		private final MineralType type;
		private double quantity;

		public FractionalMinedQuantity(final MineralType type, final double quantity) {
			super();
			this.type = type;
			this.quantity = quantity;
		}

		public MineralType getType() {
			return type;
		}

		public double getQuantity() {
			return quantity;
		}

		public void setQuantity(final double quantity) {
			this.quantity = quantity;
		}
	}
}