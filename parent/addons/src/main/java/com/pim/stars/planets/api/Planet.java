package com.pim.stars.planets.api;

import java.util.Optional;

public interface Planet {

	public String getName();

	public Optional<String> getOwnerId();
}
