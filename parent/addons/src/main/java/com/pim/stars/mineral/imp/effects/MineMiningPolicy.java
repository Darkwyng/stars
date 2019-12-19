package com.pim.stars.mineral.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.effects.MiningPolicy;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.imp.persistence.MineralRaceEntity;
import com.pim.stars.mineral.imp.persistence.MineralRaceRepository;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;

@Component
public class MineMiningPolicy implements MiningPolicy {

	@Autowired
	private PlanetMineCount planetMineCount;
	@Autowired
	private MineralRaceRepository mineralRaceRepository;

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ProductionAvailabilityCalculator productionAvailabilityCalculator;
	@Autowired
	private MineProductionItemType mineProductionItemType;
	@Autowired
	private MiningCalculator miningCalculator;

	@Override
	public CargoHolder calculateMining(final Game game, final Planet planet) {

		final boolean planetHasOwner = planet.getOwnerId().isPresent();
		if (planetHasOwner) {
			final int numberOfMines = planetMineCount.getValue(planet);
			if (numberOfMines > 0) {
				final boolean minesAreAvailable = productionAvailabilityCalculator.isProductionItemTypeAvailable(game,
						planet, mineProductionItemType);
				if (minesAreAvailable) {
					return calculateMining(game, planet, planet.getOwnerId().get(), numberOfMines);
				}
			}
		}
		return cargoProcessor.createCargoHolder();
	}

	private CargoHolder calculateMining(final Game game, final Planet planet, final String ownerId,
			final Integer numberOfMines) {

		final MineralRaceEntity owner = mineralRaceRepository.findByRaceId(ownerId); // TODO: findByGameIdAndYearAndRaceId
		final double efficiency = owner.getMineEfficiency();
		final double effectiveMines = numberOfMines * efficiency;

		return miningCalculator.calculateMining(game, planet, effectiveMines);
	}
}