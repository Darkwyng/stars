package com.pim.stars.integrationtests.race.turn;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

import java.util.Collection;
import java.util.List;
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
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;
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
	private GameRaceCollection gameRaceCollection;
	@Autowired
	private RacePrimaryRacialTrait racePrimaryRacialTrait;
	@Autowired
	private RaceSecondaryRacialTraitCollection raceSecondaryRacialTraitCollection;

	@Autowired
	private TurnCreator turnCreator;

	@Test
	public void testThatTurnGenerationTransformsRacesWithNames() {
		final Race firstRace = raceTestDataProvider.createRace("HyperExpander", "LowStartingPopulation");
		final Race secondRace = raceTestDataProvider.createRace("HyperExpander", "LowStartingPopulation");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(firstRace);
		dataRaceCollection.getValue(initializationData).add(secondRace);
		final Game game = gameInitializer.initializeGame(initializationData);
		final String firstRaceid = firstRace.getId();
		assertThat(firstRaceid, not(emptyOrNullString()));

		// Check that races have been initialized:
		final Collection<Race> raceCollection = gameRaceCollection.getValue(game);
		assertThat(raceCollection, not(empty()));

		// Check that each race has a unique id:
		final Set<String> raceNames = getRaceIds(raceCollection);
		assertThat(raceCollection.size(), is(raceNames.size()));

		// Create a turn:
		final Turn turn = turnCreator.createTurn(game, firstRace);
		assertThat(turn, not(nullValue()));

		// Check that races were transformed:
		final Collection<Race> turnRaceCollection = gameRaceCollection.getValue(turn);
		assertThat(turnRaceCollection, not(nullValue()));
		assertThat(turnRaceCollection, not(empty()));
		assertThat(turnRaceCollection.size(), is(raceCollection.size()));

		// Check that races were not just copied:
		assertThat(turnRaceCollection, not(sameInstance(raceCollection)));
		turnRaceCollection.stream()
				.forEach(turnRace -> assertThat("Races should not just be inserted into the new collection",
						raceCollection, not(hasItem(turnRace))));

		// Check that ids were transformed:
		final String raceIdString = getRaceIdString(raceCollection);
		final String turnRaceIdString = getRaceIdString(turnRaceCollection);
		assertThat(turnRaceIdString, is(raceIdString));

		// Check that traits of races is only transformed for the owner:
		// Primary trait:
		final List<String> raceIdsWithPrimaryTrait = turnRaceCollection.stream()
				.filter(race -> racePrimaryRacialTrait.getValue(race) != null).map(race -> race.getId())
				.collect(Collectors.toList());
		assertThat(raceIdsWithPrimaryTrait.size(), is(1));
		assertThat(raceIdsWithPrimaryTrait, containsInAnyOrder(firstRaceid));

		// Secondary traits:
		final List<String> raceIdsWithSecondaryTraits = turnRaceCollection.stream()
				.filter(race -> !raceSecondaryRacialTraitCollection.getValue(race).isEmpty()).map(race -> race.getId())
				.collect(Collectors.toList());
		assertThat(raceIdsWithSecondaryTraits.size(), is(1));
		assertThat(raceIdsWithSecondaryTraits, containsInAnyOrder(firstRaceid));
	}

	private String getRaceIdString(final Collection<Race> turnRaceCollection) {
		return getRaceIds(turnRaceCollection).stream().sorted().collect(Collectors.joining(", "));
	}

	private Set<String> getRaceIds(final Collection<Race> raceCollection) {
		return raceCollection.stream().map(Race::getId) //
				.peek(id -> assertThat(id, not(emptyOrNullString()))) //
				.collect(Collectors.toSet());
	}
}