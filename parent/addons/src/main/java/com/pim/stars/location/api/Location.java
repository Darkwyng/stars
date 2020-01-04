package com.pim.stars.location.api;

import java.util.stream.Stream;

import com.pim.stars.location.api.policies.LocationHolderDefinition;

public interface Location {

	public double getDistance(Location other);

	public <T> Stream<T> getLocationHoldersByType(LocationHolderDefinition<T> definition);
}
