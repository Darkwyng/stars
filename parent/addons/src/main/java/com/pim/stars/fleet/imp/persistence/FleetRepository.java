package com.pim.stars.fleet.imp.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FleetRepository extends MongoRepository<FleetEntity, FleetEntityId> {

}
