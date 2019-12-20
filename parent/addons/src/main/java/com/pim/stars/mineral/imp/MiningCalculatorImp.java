package com.pim.stars.mineral.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoHolder.CargoTransferBuilder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.extensions.PlanetFractionalMinedQuantity;
import com.pim.stars.mineral.api.extensions.PlanetFractionalMinedQuantity.FractionalMinedQuantity;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;
import com.pim.stars.planets.api.Planet;

@Component
public class MiningCalculatorImp implements MiningCalculator {

	@Autowired
	private PlanetFractionalMinedQuantity planetFractionalMinedQuantity;
	@Autowired
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private MineralProperties mineralProperties;

	@Override
	public CargoHolder calculateMining(final Game game, final Planet planet, final double effectiveMines) {
		final CargoTransferBuilder builder = cargoProcessor.createCargoHolder().transferFromNowhere();

		final Map<MineralType, Double> concentrationByType = mineralPlanetPersistenceInterface
				.getConcentrationsByType(game, planet);

		concentrationByType.entrySet().stream().forEach(concentration -> {
			final int minedQuantity = calculateMining(planet, concentration, effectiveMines);
			builder.quantity(concentration.getKey(), minedQuantity);
		});
		return builder.sum();
	}

	private int calculateMining(final Planet planet, final Map.Entry<MineralType, Double> concentration,
			final double effectiveMines) {
		final FractionalMinedQuantity fractionalMinedQuantity = planetFractionalMinedQuantity.getValue(planet)
				.getItems().stream().filter(item -> item.getType().equals(concentration.getKey())).findAny().get();
		final double oldFractionalMinedQuantity = fractionalMinedQuantity.getQuantity();

		final Double concentrationQuantity = concentration.getValue();
		final double fullMinedQuantity = oldFractionalMinedQuantity
				+ concentrationQuantity * effectiveMines / mineralProperties.getBaseConcentration();
		final int minedQuantity = floor(fullMinedQuantity);

		final int precision = mineralProperties.getFractionalMiningPrecision();
		final int newFractionalMinedQuantity = floor((fullMinedQuantity - minedQuantity) * precision) / precision;
		fractionalMinedQuantity.setQuantity(newFractionalMinedQuantity);

		return minedQuantity;
	}

	private int floor(final double fullMinedQuantity) {
		return Double.valueOf(Math.floor(fullMinedQuantity)).intValue();
	}
}