package com.pim.stars.cargo.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CargoRepository extends MongoRepository<CargoEntity, String> {

	public Collection<CargoEntity> findByGameIdAndYear(String gameId, int year);
}
