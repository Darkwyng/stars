package com.pim.stars.mineral.imp.effects;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder.CargoTransferBuilder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.effects.HomeworldInitializationPolicy;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.turn.api.Race;

@Component
public class MineralHomeworldInitializationPolicy implements HomeworldInitializationPolicy {

	private static final Random RANDOM = new Random();

	@Autowired
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;
	@Autowired
	private CargoProcessor cargoProcessor;

	@Autowired
	private ProductionAvailabilityCalculator productionAvailabilityCalculator;
	@Autowired
	private MineProductionItemType mineProductionItemType;
	@Autowired
	private MineralProperties mineralProperties;

	@Override
	public void initializeHomeworld(final Game game, final Planet planet, final Race race,
			final GameInitializationData data) {

		final Map<MineralType, Double> concentrations = mineralPlanetPersistenceInterface.initializeHomeworld(game,
				planet, getMineCountForHomeworld(game, planet));

		initializeStartingMinerals(game, planet, concentrations);
	}

	private void initializeStartingMinerals(final Game game, final Planet planet,
			final Map<MineralType, Double> concentrations) {
		final CargoTransferBuilder builder = cargoProcessor.createCargoHolder(game, planet).transferFromNowhere();

		concentrations.entrySet().stream().forEach(concentration -> {
			final Double cargoQuantity = concentration.getValue() * 4 * (0.8 + (RANDOM.nextDouble() * 0.4));
			builder.quantity(concentration.getKey(), cargoQuantity.intValue());
		});

		builder.execute();
	}

	private int getMineCountForHomeworld(final Game game, final Planet planet) {
		return productionAvailabilityCalculator.isProductionItemTypeAvailable(game, planet, mineProductionItemType)
				? mineralProperties.getNumberOfMinesToStartWith()
				: 0;
	}
}