package com.pim.stars.location.api;

import java.util.Collection;

import com.pim.stars.game.api.Game;

public interface LocationInitializer {

	public void initializeRandomLocations(Game game, Collection<?> locationHolders);
}
