package com.pim.stars.integrationtests.mineral.game;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.mineral.MineralConfiguration;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.mineral.testapi.MineralTestApiConfiguration;
import com.pim.stars.mineral.testapi.MineralTestDataAccessor;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GameConfiguration.Complete.class, RaceTestApiConfiguration.class,
		MineralConfiguration.Complete.class, PersistenceTestConfiguration.class, MineralTestApiConfiguration.class })
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
	private PlanetProvider planetProvider;
	@Autowired
	private MineralTestDataAccessor mineralTestDataAccessor;

	@Autowired
	private List<MineralType> mineralTypes;
	@Autowired
	private List<CargoType> cargoTypes;
	@Autowired
	private List<ProductionCostType> productionCostTypes;

	@Autowired
	private CargoProcessor cargoProcessor;

	@Test
	public void testThatMineralTypesAreRecognizedAsCargoTypes() {
		mineralTypes.stream().forEach(mineralType -> assertThat(cargoTypes, hasItems(mineralType)));
	}

	@Test
	public void testThatMineralTypesAreRecognizedAsProductionCostTypes() {
		mineralTypes.stream().forEach(mineralType -> assertThat(productionCostTypes, hasItems(mineralType)));
	}

	@Test
	public void testThatMineralsAreMinedDuringGameGeneration() {

		final RaceInitializationData newRace = raceTestDataProvider.createRace();

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		Game game = gameInitializer.initializeGame(initializationData);
		final Planet homeworld = getHomeworld(game);
		mineralTestDataAccessor.setPlanetMineCount(game, homeworld, 100); // 100, because 100 mines will always do some mining (no matter how low the concentration is)
		final Map<CargoType, Integer> cargoBeforeMining = collectCargo(game, homeworld);

		game = gameGenerator.generateGame(game);
		final Map<CargoType, Integer> cargoAfterMining = collectCargo(game, getHomeworld(game));

		assertThat(mineralTypes, hasSize(greaterThan(0)));
		mineralTypes.stream().peek(type -> assertThat(type, not(nullValue()))).forEach(type -> {
			final Integer after = cargoAfterMining.get(type);
			final Integer before = cargoBeforeMining.get(type);
			assertAll(type.toString(),
					() -> assertThat("Quantity after mining should not be null", after, notNullValue()),
					() -> assertThat("Quantity before mining should not be null", before, notNullValue()),
					() -> assertThat("Quantity after mining should be graeter", after, greaterThan(before)));
		});

		// TODO: test for reporting of mining
	}

	private Map<CargoType, Integer> collectCargo(final Game game, final Planet homeworld) {
		final Map<CargoType, Integer> result = new HashMap<>();
		cargoProcessor.createCargoHolder(game, homeworld).getItems().stream() //
				.peek(item -> assertThat("No item should be null", item, not(nullValue())))
				.peek(item -> assertThat("No item should have a null type", item.getType(), not(nullValue())))
				.forEach(item -> result.put(item.getType(), item.getQuantity()));

		return result;
	}

	private Planet getHomeworld(final Game game) {
		final List<Planet> allHomeworlds = planetProvider.getPlanetsByGame(game)
				.filter(planet -> planet.getOwnerId().isPresent()).collect(Collectors.toList());
		assertThat("There should only be one homeworld", allHomeworlds, hasSize(1));

		return allHomeworlds.iterator().next();
	}
}