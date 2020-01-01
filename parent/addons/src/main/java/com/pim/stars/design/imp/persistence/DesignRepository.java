package com.pim.stars.design.imp.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends MongoRepository<DesignEntity, DesignEntityId> {

}
