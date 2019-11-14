package com.pim.stars.game.imp.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<GameEntity, Long> {

	public List<GameEntity> findByGameIdAndIsLatest(String gameId, boolean isLatest);

	public List<GameEntity> findByIsLatest(boolean isLatest);
}
