package com.pim.stars.mineral.imp.persistence.race;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pim.stars.race.imp.persistence.RaceEntity.RaceEntityId;

@Repository
public interface MineralRaceRepository extends MongoRepository<MineralRaceEntity, RaceEntityId> {

	@Query("{ 'entityId.gameId' : ?0, 'entityId.raceId' : ?1 }")
	public MineralRaceEntity findByGameIdAndRaceId(String gameId, String raceid);
}
