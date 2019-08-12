package com.pim.stars.game.imp;

import java.util.HashMap;
import java.util.Map;

import com.pim.stars.game.api.GameInitializationData;

public class GameInitializationDataImp implements GameInitializationData {

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