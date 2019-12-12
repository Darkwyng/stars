package com.pim.stars.integrationtests.planets.game;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
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

import com.pim.stars.cargo.api.Cargo;
import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.planets.PlanetConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.planets.api.extensions.PlanetCargo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PlanetConfiguration.Complete.class, GameConfiguration.Complete.class,
		PersistenceTestConfiguration.class })
public class PlanetIntegrationTest {

	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private PlanetCargo planetCargo;

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
		assertThat(planetCollection.size(), is(usedNames.size()));

		// Check that each planet has a cargo-object (even though it may be empty):
		final Set<Cargo> cargoInstances = planetCollection.stream().map(planetCargo::getValue) //
				.peek(cargo -> assertThat("cargo must not be null", cargo, not(nullValue()))) //
				.collect(Collectors.toSet());
		assertThat(planetCollection.size(), is(cargoInstances.size()));
	}
}
