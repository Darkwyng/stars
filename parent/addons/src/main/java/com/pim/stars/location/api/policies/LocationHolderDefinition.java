package com.pim.stars.location.api.policies;

public interface LocationHolderDefinition<T> {

	public boolean matches(Object object);

	public String getLocationHolderType();

	public String getLocationHolderId(T object);
}
