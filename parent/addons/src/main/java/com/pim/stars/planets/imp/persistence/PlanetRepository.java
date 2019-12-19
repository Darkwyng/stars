package com.pim.stars.planets.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pim.stars.planets.imp.persistence.PlanetEntity.PlanetEntityId;

@Repository
public interface PlanetRepository extends MongoRepository<PlanetEntity, PlanetEntityId> {

	@Query("{ 'entityId.gameId' : ?0, 'entityId.year' : ?1 }")
	public Collection<PlanetEntity> findByGameIdAndYear(String gameId, int year);

	public default PlanetEntity findByGameIdAndYearAndName(final String gameId, final int year, final String name) {
		final PlanetEntityId entityId = new PlanetEntityId(gameId, year, name);
		return findById(entityId).orElseThrow(() -> new IllegalArgumentException("No planet found for" + entityId));
	}
}