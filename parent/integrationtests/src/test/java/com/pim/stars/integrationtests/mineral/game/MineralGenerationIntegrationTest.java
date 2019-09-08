package com.pim.stars.integrationtests.mineral.game;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameConfiguration;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.mineral.MineralConfiguration;
import com.pim.stars.mineral.api.extensions.PlanetIsHomeworld;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetCargo;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.TurnConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GameConfiguration.Complete.class, RaceTestApiConfiguration.class,
		MineralConfiguration.Complete.class, TurnConfiguration.Complete.class })
public class MineralGenerationIntegrationTest {

	@Autowired
	private RaceTestDataProvider raceTestDataProvider;
	@Autowired
	private GameInitializationDataRaceCollection dataRaceCollection;
	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private GameGenerator gameGenerator;

	@Autowired
	private List<MineralType> mineralTypes;

	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetIsHomeworld planetIsHomeworld;
	@Autowired
	private PlanetMineCount planetMineCount;
	@Autowired
	private PlanetCargo planetCargo;

	@Test
	public void testThatMineralsAreMinedDuringGameGeneration() {

		final Race newRace = raceTestDataProvider.createRace();

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		final Game game = gameInitializer.initializeGame(initializationData);
		final Planet homeworld = getHomeworld(game);
		planetMineCount.setValue(homeworld, 100); // 100 mines will always do some mining
		final Map<CargoType, Integer> cargoBeforeMining = collectCargo(homeworld);

		gameGenerator.generateGame(game);
		final Map<CargoType, Integer> cargoAfterMining = collectCargo(getHomeworld(game));

		assertThat(mineralTypes, hasSize(greaterThan(0)));
		mineralTypes.stream().forEach(type -> {
			assertThat(cargoAfterMining.get(type), greaterThan(cargoBeforeMining.get(type)));
		});
	}

	private Map<CargoType, Integer> collectCargo(final Planet homeworld) {
		final Map<CargoType, Integer> result = new HashMap<>();
		planetCargo.getValue(homeworld).getItems().stream()
				.forEach(entry -> result.put(entry.getType(), entry.getQuantity()));

		return result;
	}

	private Planet getHomeworld(final Game game) {
		final List<Planet> allHomeworlds = gamePlanetCollection.getValue(game).stream()
				.filter(planetIsHomeworld::getValue).collect(Collectors.toList());
		assertThat(allHomeworlds, hasSize(1));

		return allHomeworlds.iterator().next();
	}
}