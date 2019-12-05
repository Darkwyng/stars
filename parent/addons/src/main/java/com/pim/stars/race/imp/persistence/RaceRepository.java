package com.pim.stars.race.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends MongoRepository<RaceEntity, Long> {

	public RaceEntity findByRaceId(String raceId);

	public Collection<RaceEntity> findByGameId(String gameId);
}
