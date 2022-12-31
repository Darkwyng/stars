package com.pim.stars.design.imp.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pim.stars.design.imp.persistence.DesignEntity.DesignEntityId;

@Repository
public interface DesignRepository extends MongoRepository<DesignEntity, DesignEntityId> {

}
