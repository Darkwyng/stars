package com.pim.stars.cargo.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends MongoRepository<CargoEntity, CargoEntityId> {

	@Query("{ 'entityId.gameId' : ?0, 'entityId.year' : ?1 }")
	public Collection<CargoEntity> findByGameIdAndYear(String gameId, int year);

	@Query("{ 'entityId.gameId' : ?0, 'entityId.year' : ?1, 'entityId.cargoHolderType' : ?2 }")
	public Collection<CargoEntity> findByGameIdAndYearAndType(String gameId, int year, String cargoHolderType);
}
