package com.pim.stars.cargo.api.policies;

public interface CargoHolderDefinition<T> {

	public boolean matches(Object object);

	public String getCargoHolderType();

	public String getCargoHolderId(T object);
}
