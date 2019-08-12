package com.pim.stars.planets.imp;

import java.util.HashMap;
import java.util.Map;

import com.pim.stars.planets.api.Planet;

public class PlanetImp implements Planet {

	private final Map<String, Object> extensions = new HashMap<>();

	@Override
	public Object get(final String key) {
		return extensions.get(key);
	}

	@Override
	public void set(final String key, final Object value) {
		extensions.put(key, value);
	}
}
