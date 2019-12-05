package com.pim.stars.integrationtests.race.turn;

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

import com.pim.stars.cargo.CargoConfiguration;
import com.pim.stars.colonization.ColonizationConfiguration;
import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;
import com.pim.stars.turn.TurnConfiguration;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.Turn;
import com.pim.stars.turn.api.TurnCreator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GameConfiguration.Complete.class, PersistenceTestConfiguration.class,
		RaceTestApiConfiguration.class, TurnConfiguration.Complete.class, CargoConfiguration.Complete.class,
		ColonizationConfiguration.Complete.class })
public class RaceTurnCreationIntegrationTest {

	@Autowired
	private RaceTestDataProvider raceTestDataProvider;
	@Autowired
	private GameInitializationDataRaceCollection dataRaceCollection;

	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private RaceProvider raceProvider;

	@Autowired
	private TurnCreator turnCreator;

	@Test
	public void testThatTurnGenerationTransformsRacesWithNames() {
		final RaceInitializationData firstRaceInitializationData = raceTestDataProvider.createRace("HyperExpander",
				"LowStartingPopulation");
		final RaceInitializationData secondRaceInitializationData = raceTestDataProvider.createRace("HyperExpander",
				"LowStartingPopulation");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(firstRaceInitializationData);
		dataRaceCollection.getValue(initializationData).add(secondRaceInitializationData);
		final Game game = gameInitializer.initializeGame(initializationData);
		final String aRaceid = raceProvider.getRacesByGame(game).findAny().get().getId();
		assertThat(aRaceid, not(emptyOrNullString()));

		// Check that races have been initialized:
		final Collection<Race> raceCollection = raceProvider.getRacesByGame(game).collect(Collectors.toList());
		assertThat(raceCollection, not(empty()));

		// Check that each race has a unique id:
		final Set<String> raceIds = getRaceIds(raceCollection);
		assertThat(raceCollection.size(), is(raceIds.size()));

		// Create a turn:
		final Race aRace = raceProvider.getRaceById(game, aRaceid);
		final Turn turn = turnCreator.createTurn(game, aRace);
		assertThat(turn, not(nullValue()));
	}

	private Set<String> getRaceIds(final Collection<Race> raceCollection) {
		return raceCollection.stream().map(Race::getId) //
				.peek(id -> assertThat(id, not(emptyOrNullString()))) //
				.collect(Collectors.toSet());
	}
}