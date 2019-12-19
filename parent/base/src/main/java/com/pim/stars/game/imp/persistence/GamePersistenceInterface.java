package com.pim.stars.game.imp.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GamePersistenceInterface {

	@Autowired
	private GameRepository gameRepository;

	public void initializeGame(final String gameId, final int year) {
		createNewEntity(gameId, year);
	}

	public void generateGame(final String gameId, final int year) {
		setPreviousEntityToNotLatest(gameId);
		createNewEntity(gameId, year);
	}

	private void setPreviousEntityToNotLatest(final String gameId) {
		final List<GameEntity> previous = gameRepository.findByGameIdAndIsLatest(gameId, true); // List should contain one entry
		previous.stream().peek(entity -> entity.setLatest(false)).forEach(gameRepository::save);
	}

	private void createNewEntity(final String gameId, final int year) {
		final GameEntity entity = new GameEntity();
		entity.getEntityId().setGameId(gameId);
		entity.getEntityId().setYear(year);
		entity.setLatest(true);
		gameRepository.save(entity);
	}
}
