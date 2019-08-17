package com.pim.stars.integrationtests.planets.turn;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameConfiguration;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetConfiguration;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.Turn;
import com.pim.stars.turn.api.TurnConfiguration;
import com.pim.stars.turn.api.TurnCreator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PlanetConfiguration.Complete.class, GameConfiguration.Complete.class,
		TurnConfiguration.Complete.class, RaceTestApiConfiguration.class })
public class PlanetTurnCreationIntegrationTest {

	@Autowired
	private RaceTestDataProvider raceTestDataProvider;

	@Autowired
	private GameInitializationDataRaceCollection dataRaceCollection;

	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetName planetName;
	@Autowired
	private TurnCreator turnCreator;

	@Test
	public void testThatTurnGenerationTransformsPlanetsWithNames() {
		final Race race = raceTestDataProvider.createRace("HyperExpander");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(race);
		final Game game = gameInitializer.initializeGame(initializationData);

		// Check that planets have been initialized:
		final Collection<Planet> planetCollection = gamePlanetCollection.getValue(game);
		assertThat(planetCollection, not(empty()));

		// Check that each planet has a unique name:
		final Set<String> planetNames = getPlanetNames(planetCollection);
		assertThat(planetCollection.size(), is(planetNames.size()));

		// Create a turn:
		final Turn turn = turnCreator.createTurn(game, race);
		assertThat(turn, not(nullValue()));

		// Check that planets were transformed:
		final Collection<Planet> turnPlanetCollection = gamePlanetCollection.getValue(turn);
		assertThat(turnPlanetCollection, not(nullValue()));
		assertThat(turnPlanetCollection, not(empty()));
		assertThat(turnPlanetCollection.size(), is(planetCollection.size()));

		// Check that planets were not just copied:
		assertThat(turnPlanetCollection, not(sameInstance(planetCollection)));
		turnPlanetCollection.stream()
				.forEach(turnPlanet -> assertThat("Planets should not just be inserted into the new collection",
						planetCollection, not(hasItem(turnPlanet))));

		// Check that names were transformed:
		final String planetNameString = getPlanetNamesString(planetCollection);
		final String turnPlanetNameString = getPlanetNamesString(turnPlanetCollection);
		assertThat(turnPlanetNameString, is(planetNameString));
	}

	private String getPlanetNamesString(final Collection<Planet> turnPlanetCollection) {
		return getPlanetNames(turnPlanetCollection).stream().sorted().collect(Collectors.joining(", "));
	}

	private Set<String> getPlanetNames(final Collection<Planet> planetCollection) {
		return planetCollection.stream().map(planetName::getValue) //
				.peek(name -> assertThat(name, not(isEmptyOrNullString()))) //
				.collect(Collectors.toSet());
	}
}