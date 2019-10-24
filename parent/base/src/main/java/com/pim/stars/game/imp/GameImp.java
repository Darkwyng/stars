package com.pim.stars.game.imp;

import java.util.HashMap;
import java.util.Map;

import com.pim.stars.game.api.Game;

public class GameImp implements Game {

	private final Map<String, Object> extensions = new HashMap<>();

	@Override
	public Object get(final String key) {
		return extensions.get(key);
	}

	@Override
	public void set(final String key, final Object value) {
		extensions.put(key, value);
	}

	@Override
	public String getId() {
		return null; // TODO 1: impl gameId
	}

	@Override
	public int getYear() {
		return 0; // TODO 1: impl gameId
	}
}
