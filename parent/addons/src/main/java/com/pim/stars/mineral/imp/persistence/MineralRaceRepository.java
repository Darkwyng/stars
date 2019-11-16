package com.pim.stars.mineral.imp.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MineralRaceRepository extends MongoRepository<MineralRaceEntity, String> {

	public MineralRaceEntity findByRaceId(String raceid);
}
