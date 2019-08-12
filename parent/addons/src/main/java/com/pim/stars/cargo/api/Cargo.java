package com.pim.stars.cargo.api;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.cargo.api.policies.CargoType;

public class Cargo {

	private final Collection<CargoItem> items = new ArrayList<>();

	public Collection<CargoItem> getItems() {
		return items;
	}

	public interface CargoItem {

		public CargoType getType();

		public int getQuantity();
	}
}