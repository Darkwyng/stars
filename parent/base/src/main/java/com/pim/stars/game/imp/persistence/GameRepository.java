package com.pim.stars.game.imp.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<GameEntity, GameEntityId> {

	@Query("{ 'entityId.gameId' : ?0, 'isLatest' : ?1 }")
	public List<GameEntity> findByGameIdAndIsLatest(String gameId, boolean isLatest);

	public List<GameEntity> findByIsLatest(boolean isLatest);
}
