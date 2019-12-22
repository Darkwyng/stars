package com.pim.stars.cargo.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CargoRepository extends MongoRepository<CargoEntity, CargoEntityId> {

	@Query("{ 'entityId.gameId' : ?0, 'entityId.year' : ?1 }")
	public Collection<CargoEntity> findByGameIdAndYear(String gameId, int year);
}
