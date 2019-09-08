package com.pim.stars.cargo.api;

import java.util.Collection;

import com.pim.stars.dataextension.api.Entity;

public interface CargoProcessor {

	public <E extends Entity<?>> CargoHolder createCargoHolder(E entity);

	public CargoHolder createCargoHolder();

	public CargoHolder add(Collection<CargoHolder> cargoHolders);
}
