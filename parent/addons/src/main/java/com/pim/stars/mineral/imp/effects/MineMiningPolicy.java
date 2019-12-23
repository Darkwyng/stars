package com.pim.stars.mineral.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MineProductionItemTypeProvider;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.MiningCalculator.MiningCalculatorResult;
import com.pim.stars.mineral.api.effects.MiningPolicy;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;
import com.pim.stars.mineral.imp.persistence.race.MineralRaceEntity;
import com.pim.stars.mineral.imp.persistence.race.MineralRaceRepository;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;

@Component
public class MineMiningPolicy implements MiningPolicy {

	@Autowired
	private MineralRaceRepository mineralRaceRepository;
	@Autowired
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ProductionAvailabilityCalculator productionAvailabilityCalculator;
	@Autowired
	private MineProductionItemTypeProvider mineProductionItemTypeProvider;
	@Autowired
	private MiningCalculator miningCalculator;

	@Override
	public MiningPolicyResult calculateMining(final Game game, final Planet planet) {

		final boolean planetHasOwner = planet.getOwnerId().isPresent();
		if (planetHasOwner) {
			final int numberOfMines = mineralPlanetPersistenceInterface.getMineCount(game, planet);
			if (numberOfMines > 0) {
				final boolean minesAreAvailable = productionAvailabilityCalculator.isProductionItemTypeAvailable(game,
						planet, mineProductionItemTypeProvider.getMineProductionItemType());
				if (minesAreAvailable) {
					return calculateMining(game, planet, planet.getOwnerId().get(), numberOfMines);
				}
			}
		}
		return new MiningPolicyResultImp(null);
	}

	private MiningPolicyResult calculateMining(final Game game, final Planet planet, final String ownerId,
			final Integer numberOfMines) {

		final MineralRaceEntity owner = mineralRaceRepository.findByGameIdAndRaceId(game.getId(), ownerId);
		final double efficiency = owner.getMineEfficiency();
		final double effectiveMines = numberOfMines * efficiency;

		final MiningCalculatorResult calculatorResult = miningCalculator.calculateMining(game, planet, effectiveMines);
		return new MiningPolicyResultImp(calculatorResult);
	}

	private class MiningPolicyResultImp implements MiningPolicyResult {

		private final MiningCalculatorResult calculatorResult;

		public MiningPolicyResultImp(final MiningCalculatorResult calculatorResult) {
			this.calculatorResult = calculatorResult;
		}

		@Override
		public CargoHolder getMinedCargo() {
			if (calculatorResult == null) {
				return cargoProcessor.createCargoHolder(); // an empty cargo holder
			} else {
				return calculatorResult.getMinedCargo();
			}
		}

		@Override
		public void executeMining() {
			if (calculatorResult != null) {
				calculatorResult.executeMining();
			}
		}
	}
}