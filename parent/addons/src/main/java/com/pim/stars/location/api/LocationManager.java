package com.pim.stars.location.api;

import com.pim.stars.game.api.Game;

public interface LocationManager {

	public void createLocationHolder(Game game, Object newLocationHolder, Location location);

	public void moveLocationHolder(Game game, Object locationHolder, Location newLocation);

	public void deleteLocationHolder(Game game, Object locationHolder);
}