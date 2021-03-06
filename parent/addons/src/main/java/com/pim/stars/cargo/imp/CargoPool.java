package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.Collection;

public class CargoPool extends AbstractCargoHolder {

	private final Collection<CargoItem> items;

	public CargoPool(final Collection<CargoItem> items) {
		super();
		this.items = new ArrayList<>(items);
	}

	public CargoPool() {
		super();
		this.items = new ArrayList<>();
	}

	@Override
	public Collection<CargoItem> getItems() {
		return items;
	}

	@Override
	public void commitChanges() {
		// Nothing to do, because this cargo pool is not persistent.
	}
}