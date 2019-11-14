package com.pim.stars.game.imp;

import java.util.HashMap;
import java.util.Map;

import com.pim.stars.game.api.Game;

public class GameImp implements Game {

	private Map<String, Object> extensions = new HashMap<>();
	private final String gameId;
	private final int year;

	public GameImp(final String gameId, final int year) {
		this.gameId = gameId;
		this.year = year;
	}

	public GameImp(final String gameId, final int year, final GameImp game) {
		this(gameId, year);
		this.extensions = new HashMap<>(game.extensions); // TODO 2: this is a workaround for persistence of the map
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
	public String getId() {
		return gameId;
	}

	@Override
	public int getYear() {
		return year;
	}
}
