package com.pim.stars.cargo.imp.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CargoRepository extends MongoRepository<CargoEntity, String> {

}
