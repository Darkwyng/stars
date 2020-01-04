package com.pim.stars.location.imp.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.CaseFormat;

import lombok.Getter;
import lombok.Setter;

@Repository
public interface LocationRepository extends MongoRepository<LocationEntity, LocationEntityId> {

	static final String COLLECTION_NAME = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
			LocationEntity.class.getSimpleName());

	public default Collection<LocationEntity> findAllInSameLocation(final MongoTemplate mongoTemplate,
			final String gameId, final String holderTypeId, final String holderId) {

		// Match the location with the given IDs:
		final MatchOperation matchOperation = Aggregation.match(
				Criteria.where(LocationEntity.ENTITY_ID).is(new LocationEntityId(gameId, holderTypeId, holderId)));
		// Join the found location to all locations with the same coordinates:
		final LookupOperation lookupOperation = LookupOperation.newLookup().from(COLLECTION_NAME)
				.localField(LocationEntity.COORDINATES).foreignField(LocationEntity.COORDINATES)
				.as(LocationEntities.FIELD_NAME);
		// Aggregate these operations into one object:
		final Aggregation aggregation = Aggregation.newAggregation(lookupOperation, matchOperation);

		// Get the result of the operations:
		final AggregationResults<LocationEntities> aggregate = mongoTemplate.aggregate(aggregation, COLLECTION_NAME,
				LocationEntities.class);
		return aggregate.getUniqueMappedResult().getEntities();
	}

	@Getter
	@Setter
	class LocationEntities {

		private static final String FIELD_NAME = "entities";

		private Collection<LocationEntity> entities;
	}
}