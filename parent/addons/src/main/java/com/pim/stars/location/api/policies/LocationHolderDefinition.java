package com.pim.stars.location.api.policies;

import com.pim.stars.game.api.Game;

public interface LocationHolderDefinition<T> {

	public boolean matches(Object object);

	public String getLocationHolderType();

	public String getLocationHolderId(T object);

	public T toObject(Game game, String id);
}
