package com.pim.stars.mineral.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoHolder.CargoTransferBuilder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface.MineralPlanetForMining;
import com.pim.stars.planets.api.Planet;

@Component
public class MiningCalculatorImp implements MiningCalculator {

	@Autowired
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;
	@Autowired
	private List<MineralType> mineralTypes;
	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private MineralProperties mineralProperties;

	@Override
	public MiningCalculatorResult calculateMining(final Game game, final Planet planet, final double effectiveMines) {
		final CargoTransferBuilder builder = cargoProcessor.createCargoHolder().transferFromNowhere();

		final MineralPlanetForMining mineralPlanetForMining = mineralPlanetPersistenceInterface
				.getMineralPlanetForMining(game, planet);

		mineralTypes.stream().forEach(mineralType -> {

			final double concentrationQuantity = mineralPlanetForMining.getConcentration(mineralType);
			final double fractionalMinedQuantity = mineralPlanetForMining.getFractionalMinedQuantity(mineralType);

			final int minedQuantity = calculateMining(planet, effectiveMines, mineralPlanetForMining, mineralType,
					concentrationQuantity, fractionalMinedQuantity);
			builder.quantity(mineralType, minedQuantity);
		});
		final CargoHolder cargoHolder = builder.sum();
		return new MiningCalculatorResultImp(mineralPlanetForMining, cargoHolder);
	}

	private int calculateMining(final Planet planet, final double effectiveMines,
			final MineralPlanetForMining mineralPlanetForMining, final MineralType mineralType,
			final double concentrationQuantity, final double oldFractionalMinedQuantity) {

		final double fullMinedQuantity = oldFractionalMinedQuantity
				+ concentrationQuantity * effectiveMines / mineralProperties.getBaseConcentration();
		final int minedQuantity = floor(fullMinedQuantity);

		final int precision = mineralProperties.getFractionalMiningPrecision();
		final int newFractionalMinedQuantity = floor((fullMinedQuantity - minedQuantity) * precision) / precision;
		mineralPlanetForMining.setFractionalMinedQuantity(mineralType, newFractionalMinedQuantity);

		return minedQuantity;
	}

	private int floor(final double fullMinedQuantity) {
		return Double.valueOf(Math.floor(fullMinedQuantity)).intValue();
	}

	private class MiningCalculatorResultImp implements MiningCalculatorResult {

		private final MineralPlanetForMining mineralPlanetForMining;
		private final CargoHolder cargoHolder;

		public MiningCalculatorResultImp(final MineralPlanetForMining mineralPlanetForMining,
				final CargoHolder cargoHolder) {
			super();
			this.mineralPlanetForMining = mineralPlanetForMining;
			this.cargoHolder = cargoHolder;
		}

		@Override
		public CargoHolder getMinedCargo() {
			return cargoHolder;
		}

		@Override
		public void executeMining() {
			mineralPlanetPersistenceInterface.persistMineralPlanetForMining(mineralPlanetForMining);
		}
	}
}