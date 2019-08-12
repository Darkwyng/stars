package com.pim.stars.game.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.pim.stars.game.api.Game;

@Document
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
}
