package com.pim.stars.planets.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.pim.stars.planets.api.Planet;

public class PlanetImp implements Planet {

	private final Map<String, Object> extensions = new HashMap<>();
	private final String name;
	private final String ownerId;

	public PlanetImp(final String name, final String ownerId) {
		super();
		this.name = name;
		this.ownerId = ownerId;
	}

	@Override
	public Object get(final String key) {
		return extensions.get(key);
	}

	@Override
	public void set(final String key, final Object value) {
		extensions.put(key, value);
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
