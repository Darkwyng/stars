package com.pim.stars.planets.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends MongoRepository<PlanetEntity, Long> {

	public Collection<PlanetEntity> findByGameIdAndYear(String gameId, int year);

	public PlanetEntity findByGameIdAndYearAndName(String gameId, int year, String name);
}