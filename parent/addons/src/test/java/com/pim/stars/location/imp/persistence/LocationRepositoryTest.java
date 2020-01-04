package com.pim.stars.location.imp.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.location.LocationTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { LocationTestConfiguration.WithPersistence.class })
@ActiveProfiles("WithPersistence")
public class LocationRepositoryTest {

	private static final String GAME_ID = "game";
	private static final String TYPE_ID = "type";

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void testObjectsInSameLocationCanBeFound() {
		final List<LocationEntity> entities = Arrays.asList( //
				createEntity("one place", 100, 200), //
				createEntity("other place", 200, 200), //
				createEntity("another different place", 100, 100), //
				createEntity("same place", 100, 200));

		locationRepository.saveAll(entities);

		final Collection<LocationEntity> result = locationRepository.findAllInSameLocation(mongoTemplate, GAME_ID,
				TYPE_ID, "one place");
		final String actual = mapToString(result.stream());
		final String expected = mapToString(Stream.of(entities.get(0), entities.get(3)));
		assertThat(actual, is(expected));
	}

	private String mapToString(final Stream<LocationEntity> stream) {
		return stream.map(entity -> entity.getEntityId().getLocationHolderId()).sorted()
				.collect(Collectors.joining(", "));
	}

	private LocationEntity createEntity(final String id, final int x, final int y) {
		final LocationEntity entity = new LocationEntity();
		entity.setEntityId(new LocationEntityId(GAME_ID, TYPE_ID, id));
		entity.setCoordinates(new LocationEntityCoordinates(x, y));
		return entity;
	}
}