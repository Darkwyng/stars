package com.pim.stars.turn.imp;

import java.util.HashMap;
import java.util.Map;

import com.pim.stars.turn.api.Turn;

public class TurnImp implements Turn {

	private final Map<String, Object> extensions = new HashMap<>();

	@SuppressWarnings({ "unchecked", "rawtypes" }) // required, because Turn is trying to be an Entity<Game>
	@Override
	public Class getEntityClass() {
		return Turn.class;
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
		return null; // TODO 1: impl gameId
	}

	@Override
	public int getYear() {
		return 0; // TODO 1: impl gameId
	}
}
