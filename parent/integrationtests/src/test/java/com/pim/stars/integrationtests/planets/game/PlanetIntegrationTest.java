package com.pim.stars.integrationtests.planets.game;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.location.api.LocationProvider;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.planets.PlanetConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.planets.api.policies.PlanetLocationHolderDefinition;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PlanetConfiguration.Complete.class, GameConfiguration.Complete.class,
		PersistenceTestConfiguration.class })
public class PlanetIntegrationTest {

	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private LocationProvider locationProvider;
	@Autowired
	private PlanetLocationHolderDefinition planetLocationHolderDefinition;

	@Test
	public void testThatGameInitializationCreatesPlanetsWithNames() {
		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		final Game game = gameInitializer.initializeGame(initializationData);

		// Check that planets have been initialized:
		final Collection<Planet> planetCollection = planetProvider.getPlanetsByGame(game).collect(Collectors.toList());
		assertThat(planetCollection, not(empty()));

		// Check that each planet has a unique name:
		final Set<String> usedNames = planetCollection.stream().map(Planet::getName) //
				.peek(name -> assertThat(name, not(emptyOrNullString()))) //
				.collect(Collectors.toSet());
		assertThat(usedNames, hasSize(planetCollection.size()));

		// Check that each planet has a location:
		final Set<String> planetNamesAtLocations = locationProvider.getLocations(game)
				.flatMap(location -> location.getLocationHoldersByType(game, planetLocationHolderDefinition))
				.map(Planet::getName).collect(Collectors.toSet());
		assertThat(planetNamesAtLocations, hasSize(planetCollection.size()));

		// Check that each planet's location is unique:
		final Set<String> planetLocations = locationProvider.getLocations(game).filter(
				location -> location.getLocationHoldersByType(game, planetLocationHolderDefinition).count() == 1)
				.map(location -> location.getX() + "#" + location.getY()).collect(Collectors.toSet());
		assertThat(planetLocations, hasSize(planetCollection.size()));
	}
}
