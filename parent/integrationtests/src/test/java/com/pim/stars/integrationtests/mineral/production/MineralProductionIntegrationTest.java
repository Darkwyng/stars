package com.pim.stars.integrationtests.mineral.production;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.mineral.MineralConfiguration;
import com.pim.stars.mineral.api.extensions.PlanetIsHomeworld;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.production.api.PlanetProductionQueueManager;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.extensions.PlanetProductionQueue;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.TurnConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GameConfiguration.Complete.class, RaceTestApiConfiguration.class,
		MineralConfiguration.Complete.class, TurnConfiguration.Complete.class, PersistenceTestConfiguration.class })
public class MineralProductionIntegrationTest {

	@Autowired
	private RaceTestDataProvider raceTestDataProvider;
	@Autowired
	private GameInitializationDataRaceCollection dataRaceCollection;
	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private GameGenerator gameGenerator;

	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetIsHomeworld planetIsHomeworld;
	@Autowired
	private PlanetMineCount planetMineCount;
	@Autowired
	private PlanetProductionQueueManager planetProductionQueueManager;
	@Autowired
	private PlanetProductionQueue planetProductionQueue;
	@Autowired
	private MineProductionItemType mineProductionItemType;

	@Test
	public void testThatMinesCanBeBuilt() {

		final Race newRace = raceTestDataProvider.createRace();

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		final Game game = gameInitializer.initializeGame(initializationData);
		final Planet homeworld = getHomeworld(game);
		final Integer minesBeforeBuilding = planetMineCount.getValue(homeworld);
		assertThat(minesBeforeBuilding, is(10));

		planetProductionQueueManager.addToQueue(homeworld, mineProductionItemType, 3);

		gameGenerator.generateGame(game);
		final Integer minesAfterBuildingOnce = planetMineCount.getValue(homeworld);
		assertThat(minesAfterBuildingOnce, is(12));

		gameGenerator.generateGame(game);
		final Integer minesAfterBuildingTwice = planetMineCount.getValue(homeworld);
		assertThat(minesAfterBuildingTwice, is(13));

		final ProductionQueue queue = planetProductionQueue.getValue(homeworld);
		assertThat(queue.isEmpty(), is(true));

		// TODO: test for reporting of mine building
	}

	private Planet getHomeworld(final Game game) {
		final List<Planet> allHomeworlds = gamePlanetCollection.getValue(game).stream()
				.filter(planetIsHomeworld::getValue).collect(Collectors.toList());
		assertThat(allHomeworlds, hasSize(1));

		return allHomeworlds.iterator().next();
	}
}