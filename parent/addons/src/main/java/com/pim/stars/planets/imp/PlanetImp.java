package com.pim.stars.planets.imp;

import java.util.Optional;

import com.pim.stars.planets.api.Planet;

public class PlanetImp implements Planet {

	private final String name;
	private final String ownerId;

	public PlanetImp(final String name, final String ownerId) {
		super();
		this.name = name;
		this.ownerId = ownerId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<String> getOwnerId() {
		return Optional.ofNullable(ownerId);
	}
}
