package com.pim.stars.planets.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public class PlanetImp implements Planet {

	private static final Map<String, Map<String, Map<String, Object>>> EXTENSIONS_PER_GAME_AND_PLANET_NAME = new HashMap<>();
	private final Game game;
	private final String name;
	private final String ownerId;

	public PlanetImp(final Game game, final String name, final String ownerId) {
		super();
		this.game = game;
		this.name = name;
		this.ownerId = ownerId;
	}

	private Map<String, Object> getExtensions() { // TODO: remove with last extensions for planets
		final Map<String, Map<String, Object>> extensionsForThisGame = EXTENSIONS_PER_GAME_AND_PLANET_NAME
				.computeIfAbsent(getGameKey(game), key -> new HashMap<>());

		final Map<String, Object> extensionsForThisPlanet = extensionsForThisGame.computeIfAbsent(name,
				key -> new HashMap<>());

		return extensionsForThisPlanet;
	}

	private static String getGameKey(final Game game) {
		return game.getId() + "#" + game.getYear();
	}

	public static void generateGame(final Game previousYear, final Game currentYear) {
		final Map<String, Map<String, Object>> extensionsForPreviousYear = EXTENSIONS_PER_GAME_AND_PLANET_NAME
				.computeIfAbsent(getGameKey(previousYear), key -> new HashMap<>());

		EXTENSIONS_PER_GAME_AND_PLANET_NAME.put(getGameKey(currentYear), extensionsForPreviousYear);
	}

	@Override
	public Object get(final String key) {
		return getExtensions().get(key);
	}

	@Override
	public void set(final String key, final Object value) {
		getExtensions().put(key, value);
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
