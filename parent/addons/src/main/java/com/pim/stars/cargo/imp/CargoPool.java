package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.cargo.api.Cargo.CargoItem;

public class CargoPool extends AbstractCargoHolder {

	private final Collection<CargoItem> items;

	public CargoPool(final Collection<CargoItem> items) {
		super();
		this.items = items;
	}

	public CargoPool() {
		this(new ArrayList<>());
	}

	@Override
	public Collection<CargoItem> getItems() {
		return items;
	}
}