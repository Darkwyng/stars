package com.pim.stars.location.api;

import java.util.stream.Stream;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.policies.LocationHolderDefinition;

public interface Location {

	public int getX();

	public int getY();

	public double getDistance(final Location other);

	public int getDistanceForComparison(final Location other);

	public <T> Stream<T> getLocationHoldersByType(Game game, LocationHolderDefinition<T> definition);

	public boolean isSameLocation(Location other);
}