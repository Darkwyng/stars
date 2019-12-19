package com.pim.stars.mineral.imp.effects;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder.CargoTransferBuilder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.mineral.api.extensions.PlanetIsHomeworld;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.effects.HomeworldInitializationPolicy;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.turn.api.Race;

@Component
public class MineralHomeworldInitializationPolicy implements HomeworldInitializationPolicy {

	private static final Random RANDOM = new Random();

	@Autowired
	private PlanetMineralConcentrations planetMineralConcentrations;
	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private PlanetMineCount planetMineCount;
	@Autowired
	private PlanetIsHomeworld planetIsHomeworld;

	@Autowired
	private ProductionAvailabilityCalculator productionAvailabilityCalculator;
	@Autowired
	private MineProductionItemType mineProductionItemType;
	@Autowired
	private MineralProperties mineralProperties;

	@Override
	public void initializeHomeworld(final Game game, final Planet planet, final Race race,
			final GameInitializationData data) {
		initializeStartingMinerals(game, planet);
		initializeStartingMines(game, planet);
		planetIsHomeworld.setValue(planet, true);
	}

	private void initializeStartingMinerals(final Game game, final Planet planet) {
		final CargoTransferBuilder builder = cargoProcessor.createCargoHolder(game, planet).transferFromNowhere();

		planetMineralConcentrations.getValue(planet).forEach(concentration -> {
			final Double cargoQuantity = concentration.getAmount() * 4 * (0.8 + (RANDOM.nextDouble() * 0.4));
			builder.quantity(concentration.getType(), cargoQuantity.intValue());
		});

		builder.execute();
	}

	private void initializeStartingMines(final Game game, final Planet planet) {
		final Integer newMineCount;
		newMineCount = productionAvailabilityCalculator.isProductionItemTypeAvailable(game, planet,
				mineProductionItemType) ? mineralProperties.getNumberOfMinesToStartWith() : 0;
		planetMineCount.setValue(planet, newMineCount);
	}
}