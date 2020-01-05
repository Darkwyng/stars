package com.pim.stars.location.api;

import java.util.stream.Stream;

import com.pim.stars.game.api.Game;

public interface LocationProvider {

	public Location getLocationByCoordinates(Game game, int x, int y);

	public Stream<Location> getLocations(Game game);

	public Location getLocationByHolder(Game game, Object locationHolder);

	public Location reloadLocation(Game game, Location location);
}
