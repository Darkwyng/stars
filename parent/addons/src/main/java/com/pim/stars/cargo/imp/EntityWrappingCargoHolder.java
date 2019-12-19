package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityWrappingCargoHolder extends AbstractCargoHolder {

	private final Supplier<Collection<CargoItem>> itemSupplier;
	private final Consumer<Collection<CargoItem>> itemConsumer;

	private Collection<CargoItem> items = null;

	public EntityWrappingCargoHolder(final Supplier<Collection<CargoItem>> itemSupplier,
			final Consumer<Collection<CargoItem>> itemConsumer) {
		super();
		this.itemSupplier = itemSupplier;
		this.itemConsumer = itemConsumer;
	}

	@Override
	public Collection<CargoItem> getItems() {
		if (items == null) {
			items = new ArrayList<>(itemSupplier.get());
		}
		return items;
	}

	@Override
	public void commitChanges() {
		if (items != null) {
			itemConsumer.accept(items);
		}
	}
}
