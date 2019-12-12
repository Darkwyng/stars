package com.pim.stars.integrationtests.mineral.game;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.collection.IsIterableWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.Cargo.CargoItem;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.GameConfiguration;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.mineral.MineralConfiguration;
import com.pim.stars.mineral.api.extensions.PlanetIsHomeworld;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations.MineralConcentrations;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.testapi.RaceTestApiConfiguration;
import com.pim.stars.race.testapi.RaceTestDataProvider;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GameConfiguration.Complete.class, RaceTestApiConfiguration.class,
		MineralConfiguration.Complete.class, PersistenceTestConfiguration.class })
public class MineralInitializationIntegrationTest {

	@Autowired
	private RaceTestDataProvider raceTestDataProvider;
	@Autowired
	private GameInitializationDataRaceCollection dataRaceCollection;
	@Autowired
	private GameInitializer gameInitializer;
	@Autowired
	private PlanetProvider planetProvider;

	@Autowired
	private List<MineralType> mineralTypes;

	@Autowired
	private PlanetIsHomeworld planetIsHomeworld;
	@Autowired
	private PlanetMineCount planetMineCount;
	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private PlanetMineralConcentrations planetMineralConcentrations;

	@Test
	public void testThatPlanetsAreinitializedWithConcentrationsMinesAndMinerals() {
		assertThat(mineralTypes, hasSize(3));

		final RaceInitializationData newRace = raceTestDataProvider.createRace("HyperExpander");

		final GameInitializationData initializationData = gameInitializer.createNewGameInitializationData();
		dataRaceCollection.getValue(initializationData).add(newRace);

		final Game game = gameInitializer.initializeGame(initializationData);

		planetProvider.getPlanetsByGame(game).forEach(this::checkPlanet);

		checkHomeworld(game);

	}

	private void checkPlanet(final Planet planet) {
		final MineralConcentrations concentrations = planetMineralConcentrations.getValue(planet);
		assertThat(concentrations, not(nullValue()));
		assertThat(concentrations, IsIterableWithSize.iterableWithSize(3));
		concentrations.forEach(conc -> {
			assertThat(conc.getType(), not(nullValue()));
			assertThat(conc.getAmount(), greaterThan(0.0));
		});
	}

	private void checkHomeworld(final Game game) {
		final List<Planet> allHomeworlds = planetProvider.getPlanetsByGame(game).filter(planetIsHomeworld::getValue)
				.collect(Collectors.toList());
		assertThat(allHomeworlds, hasSize(1));

		final Planet homeworld = allHomeworlds.iterator().next();
		assertThat(planetMineCount.getValue(homeworld), greaterThan(0));
		final Collection<CargoItem> cargoItems = cargoProcessor.createCargoHolder(homeworld).getItems().stream()
				.filter(item -> item.getType() instanceof MineralType).collect(Collectors.toList());
		assertThat(cargoItems, hasSize(3));
		cargoItems.forEach(conc -> {
			assertThat(conc.getQuantity(), greaterThan(0));
		});
	}
}