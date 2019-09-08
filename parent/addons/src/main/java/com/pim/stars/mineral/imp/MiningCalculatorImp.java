package com.pim.stars.mineral.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoHolder.CargoTransferBuilder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.extensions.PlanetFractionalMinedQuantity;
import com.pim.stars.mineral.api.extensions.PlanetFractionalMinedQuantity.FractionalMinedQuantity;
import com.pim.stars.mineral.api.extensions.PlanetIsHomeworld;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations.MineralConcentration;
import com.pim.stars.planets.api.Planet;

@Component
public class MiningCalculatorImp implements MiningCalculator {

	@Autowired
	private PlanetFractionalMinedQuantity planetFractionalMinedQuantity;
	@Autowired
	private PlanetIsHomeworld planetIsHomeworld;
	@Autowired
	private PlanetMineralConcentrations planetMineralConcentrations;

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private MineralProperties mineralProperties;

	@Override
	public CargoHolder calculateMining(final Game game, final Planet planet, final double effectiveMines) {
		final CargoTransferBuilder builder = cargoProcessor.createCargoHolder().transferFromNowhere();
		planetMineralConcentrations.getValue(planet).forEach(concentration -> {
			final int minedQuantity = executeMining(planet, concentration, effectiveMines);
			builder.quantity(concentration.getType(), minedQuantity);
		});
		return builder.sum();
	}

	private int executeMining(final Planet planet, final MineralConcentration concentration,
			final double effectiveMines) {
		final FractionalMinedQuantity fractionalMinedQuantity = planetFractionalMinedQuantity.getValue(planet)
				.getItems().stream().filter(item -> item.getType().equals(concentration.getType())).findAny().get();
		final double oldFractionalMinedQuantity = fractionalMinedQuantity.getQuantity();

		final double fullMinedQuantity = oldFractionalMinedQuantity + getConcentrationAmount(planet, concentration)
				* effectiveMines / mineralProperties.getBaseConcentration();
		final int minedQuantity = floor(fullMinedQuantity);

		final int newFractionalMinedQuantity = floor(
				(fullMinedQuantity - minedQuantity) * mineralProperties.getFractionMiningPrecision())
				/ mineralProperties.getFractionMiningPrecision();
		fractionalMinedQuantity.setQuantity(newFractionalMinedQuantity);

		return minedQuantity;
	}

	private double getConcentrationAmount(final Planet planet, final MineralConcentration concentration) {
		if (planetIsHomeworld.getValue(planet)) {
			return Math.max(concentration.getAmount(), mineralProperties.getHomeWorldMinimumConcentration());
		} else {
			return concentration.getAmount();
		}
	}

	private int floor(final double fullMinedQuantity) {
		return Double.valueOf(Math.floor(fullMinedQuantity)).intValue();
	}
}