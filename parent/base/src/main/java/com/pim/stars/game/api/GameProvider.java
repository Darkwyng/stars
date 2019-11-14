package com.pim.stars.game.api;

import java.util.Optional;
import java.util.stream.Stream;

public interface GameProvider {

	public Stream<Game> getAllGames();

	public Optional<Game> getGameWithLatestYearById(String gameId);
}
