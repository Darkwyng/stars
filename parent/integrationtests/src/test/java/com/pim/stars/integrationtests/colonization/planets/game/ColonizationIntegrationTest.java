package com.pim.stars.integrationtests.colonization.planets.game;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.colonization.api.ColonistCalculator;
import com.pim.stars.colonization.api.ColonizationConfiguration;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameConfiguration;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;
import com.pim.stars.turn.api.Race;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ColonizationConfiguration.Complete.class, GameConfiguration.Complete.class,
		RaceTestApiConfiguration.class })
public class ColonizationIntegrationTest {

	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private GameGenerator gameGenerator;
	@Autowired
	private ColonistCalculator colonistCalculator;
	@Autowired
	private ColonizationProperties colonizationProperties;

	@Autowired
	private RaceTestDataProvider raceTestDataProvider;

	@Autowired
	private GameInitializationDataRaceCollection dataRaceCollection;
	@Autowired
	private GameRaceCollection gameRaceCollection;
	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetOwnerId planetOwnerId;

	@Test
	public void testThatHomeworldsAreInitializedWithColonistsAndThatColonistsGrow() {
		final Race newRace = raceTestDataProvider.createRace("HyperExpander");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		final Game game = gameInitializer.initializeGame(initializationData);
		assertThat("Races should have been copied to the game.", gameRaceCollection.getValue(game),
				not(anyOf(empty(), nullValue())));
		final Integer initialPopulation = getHomeworldPopulation(game);
		assertThat(initialPopulation, is(colonizationProperties.getDefaultInitialPopulation()));

		gameGenerator.generateGame(game);
		final Integer increasedPopulation = getHomeworldPopulation(game);

		assertThat(increasedPopulation, greaterThan(initialPopulation));
	}

	@Test
	public void testThatLowStartinPopulationTraitLowersPopulation() {
		final Race newRace = raceTestDataProvider.createRace("HyperExpander", "LowStartingPopulation");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		final Game game = gameInitializer.initializeGame(initializationData);
		final Integer initialPopulation = getHomeworldPopulation(game);
		assertThat(initialPopulation, lessThan(colonizationProperties.getDefaultInitialPopulation()));
	}

	private Integer getHomeworldPopulation(final Game game) {
		// Check that planets exist:
		final Collection<Planet> planetCollection = gamePlanetCollection.getValue(game);
		assertThat("There should be planets", planetCollection, not(empty()));

		// Check that a homeworld has been initialized:
		final List<String> homeworldOwnerIds = planetCollection.stream().map(planet -> planetOwnerId.getValue(planet))
				.filter(ownerId -> ownerId != null).collect(Collectors.toList());
		assertThat("There should be a homeworld with an owner", homeworldOwnerIds, hasSize(1));

		// Check that population has been set:
		final List<Integer> homeworldPopulations = planetCollection.stream()
				.map(planet -> colonistCalculator.getCurrentPlanetPopulation(planet))
				.filter(population -> population > 0).collect(Collectors.toList());
		assertThat("There should be a homeworld with a population", homeworldPopulations, hasSize(1));

		return homeworldPopulations.iterator().next();
	}
}