package com.pim.stars.game.imp.persistence;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameProvider;
import com.pim.stars.game.imp.GameImp;

@Component
public class GameProviderImp implements GameProvider {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public Game getGame(final String gameId, final int year) {
		return new GameImp(gameId, year);
	}

	@Override
	public Stream<Game> getAllGames() {
		return gameRepository.findByIsLatest(true).stream().map(this::mapEntityToGame);
	}

	@Override
	public Optional<Game> getGameWithLatestYearById(final String gameId) {
		return gameRepository.findByGameIdAndIsLatest(gameId, true).stream()
				.sorted(Comparator.comparing(entity -> ((GameEntity) entity).getEntityId().getYear()).reversed())
				.map(this::mapEntityToGame).findFirst();
	}

	private Game mapEntityToGame(final GameEntity entity) {
		return new GameImp(entity.getEntityId().getGameId(), entity.getEntityId().getYear());
	}

}