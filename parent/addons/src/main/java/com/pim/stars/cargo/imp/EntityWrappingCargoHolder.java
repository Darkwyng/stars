package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import com.pim.stars.cargo.api.Cargo.CargoItem;

public class EntityWrappingCargoHolder extends AbstractCargoHolder {

	private final Supplier<Collection<CargoItem>> itemSupplier;
	private Collection<CargoItem> items = null;

	public EntityWrappingCargoHolder(final Supplier<Collection<CargoItem>> itemSupplier) {
		super();
		this.itemSupplier = itemSupplier;
	}

	@Override
	public Collection<CargoItem> getItems() {
		if (items == null) {
			items = new ArrayList<>(itemSupplier.get());
		}
		return items;
	}

	@Override
	public boolean isEmpty() {
		return getItems().stream().findAny().isEmpty();
	}
}
