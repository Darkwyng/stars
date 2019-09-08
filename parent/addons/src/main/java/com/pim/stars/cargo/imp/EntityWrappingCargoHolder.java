package com.pim.stars.cargo.imp;

import java.util.Collection;
import java.util.function.Supplier;

import com.pim.stars.cargo.api.Cargo;
import com.pim.stars.cargo.api.Cargo.CargoItem;
import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

public class EntityWrappingCargoHolder extends AbstractCargoHolder {

	private final Entity<?> entity;
	private final Supplier<CargoDataExtensionPolicy<?>> policySupplier;

	public EntityWrappingCargoHolder(final Entity<?> entity,
			final Supplier<CargoDataExtensionPolicy<?>> cargoDataExtensionPolicy) {
		super();
		this.entity = entity;
		this.policySupplier = cargoDataExtensionPolicy;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<CargoItem> getItems() {
		final DataExtensionPolicy policy = policySupplier.get();
		final Cargo cargo = (Cargo) policy.getValue(entity);
		return cargo.getItems();
	}
}
