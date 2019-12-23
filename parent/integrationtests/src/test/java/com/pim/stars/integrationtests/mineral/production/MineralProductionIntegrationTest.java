package com.pim.stars.integrationtests.mineral.production;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
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
import com.pim.stars.mineral.api.MineProductionItemTypeProvider;
import com.pim.stars.mineral.imp.reports.PlanetHasBuiltMinesReport;
import com.pim.stars.mineral.testapi.MineralTestApiConfiguration;
import com.pim.stars.mineral.testapi.MineralTestDataAccessor;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.api.PlanetProductionQueueManager;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;
import com.pim.stars.report.api.ReportProvider;
import com.pim.stars.turn.api.Race;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GameConfiguration.Complete.class, RaceTestApiConfiguration.class,
		MineralConfiguration.Complete.class, PersistenceTestConfiguration.class, MineralTestApiConfiguration.class })
public class MineralProductionIntegrationTest {

	@Autowired
	private RaceTestDataProvider raceTestDataProvider;
	@Autowired
	private GameInitializationDataRaceCollection dataRaceCollection;
	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private RaceProvider raceProvider;
	@Autowired
	private GameGenerator gameGenerator;
	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private MineralTestDataAccessor mineralTestDataAccessor;

	@Autowired
	private PlanetProductionQueueManager planetProductionQueueManager;
	@Autowired
	private MineProductionItemTypeProvider mineProductionItemTypeProvider;
	@Autowired
	private ReportProvider reportProvider;

	@Test
	public void testThatMinesCanBeBuilt() {

		final RaceInitializationData newRaceInitializationData = raceTestDataProvider.createRace();

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRaceInitializationData);

		Game game = gameInitializer.initializeGame(initializationData);
		final Race newRace = raceProvider.getRacesByGame(game).findAny().get();
		final Planet homeworld = getHomeworld(game);
		final Integer minesBeforeBuilding = mineralTestDataAccessor.getPlanetMineCount(game, homeworld);
		assertThat(minesBeforeBuilding, is(10));
		assertReports(game, newRace, not(hasPlanetHasBuiltMinesReport()));

		planetProductionQueueManager.addToQueue(game, homeworld,
				mineProductionItemTypeProvider.getMineProductionItemType(), 3);

		game = gameGenerator.generateGame(game);
		final Integer minesAfterBuildingOnce = mineralTestDataAccessor.getPlanetMineCount(game, homeworld);
		assertThat(minesAfterBuildingOnce, is(12));
		assertReports(game, newRace, hasPlanetHasBuiltMinesReport());

		game = gameGenerator.generateGame(game);
		final Integer minesAfterBuildingTwice = mineralTestDataAccessor.getPlanetMineCount(game, homeworld);
		assertThat(minesAfterBuildingTwice, is(13));
		assertReports(game, newRace, hasPlanetHasBuiltMinesReport());

		game = gameGenerator.generateGame(game);
		assertReports(game, newRace, not(hasPlanetHasBuiltMinesReport()));
	}

	private Matcher<Iterable<? super Object>> hasPlanetHasBuiltMinesReport() {
		return hasItem(hasProperty("type", is(PlanetHasBuiltMinesReport.class.getName())));
	}

	private void assertReports(final Game game, final Race newRace, final Matcher<Iterable<? super Object>> matcher) {
		final List<Object> collection = reportProvider.getReports(game, newRace, Locale.ENGLISH)
				.collect(Collectors.toList());
		assertThat(collection, matcher);
	}

	private Planet getHomeworld(final Game game) {
		final List<Planet> allHomeworlds = planetProvider.getPlanetsByGame(game)
				.filter(planet -> planet.getOwnerId().isPresent()).collect(Collectors.toList());
		assertThat("There should only be one homeworld", allHomeworlds, hasSize(1));

		return allHomeworlds.iterator().next();
	}
}