package com.pim.stars.cargo.api;

import com.pim.stars.dataextension.api.Entity;

public interface CargoProcessor {

	public <E extends Entity<?>> CargoHolder createCargoHolder(E entity);
}
