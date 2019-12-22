package com.pim.stars.race.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pim.stars.race.imp.persistence.RaceEntity.RaceEntityId;

@Repository
public interface RaceRepository extends MongoRepository<RaceEntity, RaceEntityId> {

	@Query("{ 'entityId.gameId' : ?0, 'entityId.raceId' : ?1 }")
	public RaceEntity findByGameIdAndRaceId(String gameId, String raceId);

	@Query("{ 'entityId.gameId' : ?0 }")
	public Collection<RaceEntity> findByGameId(String gameId);
}
