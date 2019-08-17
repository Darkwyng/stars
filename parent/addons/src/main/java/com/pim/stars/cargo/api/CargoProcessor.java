package com.pim.stars.cargo.api;

import com.pim.stars.dataextension.api.Entity;

public interface CargoProcessor {

	public <T extends Entity<?>, S extends T> CargoHolder createCargoHolder(final S entity, final Class<T> entityClass);
}
