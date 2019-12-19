package com.pim.stars.integrationtests.colonization.planets.game;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.colonization.ColonizationConfiguration;
import com.pim.stars.colonization.api.ColonistCalculator;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ColonizationConfiguration.Complete.class, GameConfiguration.Complete.class,
		PersistenceTestConfiguration.class, RaceTestApiConfiguration.class })
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
	private PlanetProvider planetProvider;

	@Test
	public void testThatHomeworldsAreInitializedWithColonistsAndThatColonistsGrow() {
		final RaceInitializationData newRace = raceTestDataProvider.createRace("HyperExpander");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		Game game = gameInitializer.initializeGame(initializationData);
		final Integer initialPopulation = getHomeworldPopulation(game);
		assertThat(initialPopulation, is(colonizationProperties.getDefaultInitialPopulation()));

		game = gameGenerator.generateGame(game);
		final Integer increasedPopulation = getHomeworldPopulation(game);

		assertThat(increasedPopulation, greaterThan(initialPopulation));
	}

	@Test
	public void testThatLowStartinPopulationTraitLowersPopulation() {
		final RaceInitializationData newRace = raceTestDataProvider.createRace("HyperExpander",
				"LowStartingPopulation");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		final Game game = gameInitializer.initializeGame(initializationData);
		final Integer initialPopulation = getHomeworldPopulation(game);
		assertThat(initialPopulation, lessThan(colonizationProperties.getDefaultInitialPopulation()));
	}

	private Integer getHomeworldPopulation(final Game game) {
		// Check that planets exist:
		final Collection<Planet> planetCollection = planetProvider.getPlanetsByGame(game).collect(Collectors.toList());
		assertThat("There should be planets", planetCollection, not(empty()));

		// Check that a homeworld has been initialized:
		final List<String> homeworldOwnerIds = planetCollection.stream().map(Planet::getOwnerId)
				.filter(Optional<String>::isPresent).map(Optional<String>::get).collect(Collectors.toList());
		assertThat("There should be a homeworld with an owner", homeworldOwnerIds, hasSize(1));

		// Check that population has been set:
		final List<Integer> homeworldPopulations = planetCollection.stream()
				.map(planet -> colonistCalculator.getCurrentPlanetPopulation(game, planet))
				.filter(population -> population > 0).collect(Collectors.toList());
		assertThat("There should be a homeworld with a population", homeworldPopulations, hasSize(1));

		return homeworldPopulations.iterator().next();
	}
}