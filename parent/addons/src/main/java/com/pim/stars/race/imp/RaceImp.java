package com.pim.stars.race.imp;

import java.util.HashMap;
import java.util.Map;

import com.pim.stars.race.api.Race;

public class RaceImp implements Race {

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
