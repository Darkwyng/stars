package com.pim.stars.location.api;

import java.util.stream.Stream;

import com.pim.stars.game.api.Game;

public interface LocationProvider {

	public Stream<Location> getLocations(Game game);

	public Location getLocationByHolder(Object locationHolder);
}
