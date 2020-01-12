package com.pim.stars.location.imp.persistence.location;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.LocationTestConfiguration;
import com.pim.stars.location.api.Location;
import com.pim.stars.location.api.LocationManager;
import com.pim.stars.location.api.LocationProvider;
import com.pim.stars.location.api.policies.LocationHolderDefinition;
import com.pim.stars.location.imp.persistence.location.LocationPersistenceComponentTest.TestConfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@ActiveProfiles("WithPersistence")
public class LocationPersistenceComponentTest {

	@Autowired
	private LocationManager manager;
	@Autowired
	private LocationProvider provider;
	@Autowired
	private LocationHolderDefinition<FirstTestObject> firstDefinition;
	@Autowired
	private LocationHolderDefinition<SecondTestObject> secondDefinition;

	@Mock
	private Game game;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(game.getId()).thenReturn("theGameId");
	}

	@Test
	public void testLocationsCanBeStoredAndChangedAndRetrieved() {
		final FirstTestObject firstA = new FirstTestObject("A");
		final FirstTestObject firstB = new FirstTestObject("B");
		final SecondTestObject secondA = new SecondTestObject("A");

		Location location17_19 = provider.getLocationByCoordinates(game, 17, 19);
		Location location19_17 = provider.getLocationByCoordinates(game, 19, 17);
		assertThat(location17_19.getX(), is(17));
		assertThat(location17_19.getY(), is(19));
		assertLocationHolders(location17_19, firstDefinition);
		assertLocationHolders(location19_17, secondDefinition);

		// Create
		manager.createLocationHolder(game, firstA, location17_19);
		manager.createLocationHolder(game, secondA, location17_19);
		manager.createLocationHolder(game, firstB, location19_17);

		location17_19 = provider.getLocationByCoordinates(game, 17, 19);
		assertLocationHolders(location17_19, firstDefinition, "A");
		assertLocationHolders(location17_19, secondDefinition, "A");

		location19_17 = provider.getLocationByCoordinates(game, 19, 17);
		assertLocationHolders(location19_17, firstDefinition, "B");
		assertLocationHolders(location19_17, secondDefinition);

		Location firstALocation = provider.getLocationByHolder(game, firstA);
		assertLocationHolders(firstALocation, firstDefinition, "A");
		assertLocationHolders(firstALocation, secondDefinition, "A");

		assertLocationsAreFoundByGame(location17_19, location19_17);

		// Move:
		manager.moveLocationHolder(game, firstB, firstALocation);
		firstALocation = provider.getLocationByHolder(game, firstA);
		assertLocationHolders(firstALocation, firstDefinition, "A", "B");
		assertLocationHolders(firstALocation, secondDefinition, "A");

		location19_17 = provider.getLocationByCoordinates(game, 19, 17);
		assertLocationHolders(location19_17, firstDefinition);
		assertLocationHolders(location19_17, secondDefinition);

		assertLocationsAreFoundByGame(firstALocation);

		// Delete:
		manager.deleteLocationHolder(game, firstB);
		firstALocation = provider.getLocationByHolder(game, firstA);
		assertLocationHolders(firstALocation, firstDefinition, "A");
		assertLocationHolders(firstALocation, secondDefinition, "A");
		assertLocationsAreFoundByGame(firstALocation);

		manager.deleteLocationHolder(game, secondA);
		firstALocation = provider.getLocationByHolder(game, firstA);
		assertLocationHolders(firstALocation, firstDefinition, "A");
		assertLocationHolders(firstALocation, secondDefinition);
		assertLocationsAreFoundByGame(firstALocation);

		manager.deleteLocationHolder(game, firstA);
		firstALocation = provider.reloadLocation(game, firstALocation);
		assertLocationHolders(firstALocation, firstDefinition);
		assertLocationHolders(firstALocation, secondDefinition);
		assertLocationsAreFoundByGame();
	}

	private void assertLocationsAreFoundByGame(final Location... locations) {
		Arrays.stream(locations).forEach(location -> {
			final Optional<Location> optional = provider.getLocations(game)
					.filter(candidate -> candidate.isSameLocation(location)).findAny();
			assertThat(location + " should exist", optional.isPresent(), is(true));
		});
		final long actualNumber = provider.getLocations(game).count();
		final long expectedNumber = locations.length;
		assertThat(actualNumber, is(expectedNumber));
	}

	private <T> void assertLocationHolders(final Location location, final LocationHolderDefinition<T> definition,
			final String... expectedIds) {
		final String expected = Arrays.stream(expectedIds).sorted().collect(Collectors.joining(", "));
		final String actual = location.getLocationHoldersByType(game, definition).map(definition::getLocationHolderId)
				.sorted().collect(Collectors.joining(", "));
		assertThat(actual, is(expected));
	}

	@Configuration
	@Import({ LocationTestConfiguration.WithPersistence.class })
	protected static class TestConfiguration {

		@Bean
		public LocationHolderDefinition<FirstTestObject> firstLocationHolderDefinition() {
			return new LocationHolderDefinition<FirstTestObject>() {

				@Override
				public boolean matches(final Object object) {
					return object instanceof FirstTestObject;
				}

				@Override
				public String getLocationHolderType() {
					return FirstTestObject.class.getSimpleName();
				}

				@Override
				public String getLocationHolderId(final FirstTestObject object) {
					return object.getId();
				}

				@Override
				public FirstTestObject toObject(Game game, final String id) {
					return new FirstTestObject(id);
				}

			};
		}

		@Bean
		public LocationHolderDefinition<SecondTestObject> secondLocationHolderDefinition() {
			return new LocationHolderDefinition<SecondTestObject>() {

				@Override
				public boolean matches(final Object object) {
					return object instanceof SecondTestObject;
				}

				@Override
				public String getLocationHolderType() {
					return SecondTestObject.class.getSimpleName();
				}

				@Override
				public String getLocationHolderId(final SecondTestObject object) {
					return object.getId();
				}

				@Override
				public SecondTestObject toObject(Game game, final String id) {
					return new SecondTestObject(id);
				}
			};
		}
	}

	@Getter
	@AllArgsConstructor
	@ToString
	protected static class FirstTestObject {

		private final String id;
	}

	@Getter
	@AllArgsConstructor
	@ToString
	protected static class SecondTestObject {

		private final String id;
	}
}