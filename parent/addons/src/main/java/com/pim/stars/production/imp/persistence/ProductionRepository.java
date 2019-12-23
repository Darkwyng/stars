package com.pim.stars.production.imp.persistence;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends MongoRepository<ProductionEntity, PlanetEntityId> {

	@Query("{ 'entityId.gameId' : ?0, 'entityId.year' : ?1 }")
	public Stream<ProductionEntity> findByGameIdAndYear(String gameId, int year);
}
