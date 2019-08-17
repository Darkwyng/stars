package com.pim.stars.cargo.api.extensions;

import java.util.Optional;

import com.pim.stars.cargo.api.Cargo;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

public interface CargoDataExtensionPolicy<E extends Entity<?>> extends DataExtensionPolicy<E, Cargo> {

	@Override
	default Optional<? extends Cargo> getDefaultValue() {
		return Optional.of(new Cargo());
	}
}