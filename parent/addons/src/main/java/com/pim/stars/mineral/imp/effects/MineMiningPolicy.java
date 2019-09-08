package com.pim.stars.mineral.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.MiningCalculator;
import com.pim.stars.mineral.api.effects.MiningPolicy;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.api.extensions.RaceMiningSettings;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.turn.api.Race;

@Component
public class MineMiningPolicy implements MiningPolicy {

	@Autowired
	private PlanetMineCount planetMineCount;
	@Autowired
	private PlanetOwnerId planetOwnerId;
	@Autowired
	private GameRaceCollection gameRaceCollection;
	@Autowired
	private RaceId raceId;
	@Autowired
	private RaceMiningSettings raceMiningSettings;

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

		final String ownerId = planetOwnerId.getValue(planet);
		final boolean planetHasOwner = ownerId != null;
		if (planetHasOwner) {
			final Integer numberOfMines = planetMineCount.getValue(planet);
			if (numberOfMines > 0) {
				final boolean minesAreAvailable = productionAvailabilityCalculator.isProductionItemTypeAvailable(game,
						planet, mineProductionItemType);
				if (minesAreAvailable) {
					final Race owner = gameRaceCollection.getValue(game).stream()
							.filter(race -> raceId.getValue(race).equals(ownerId)).findAny().get();
					final double efficiency = raceMiningSettings.getValue(owner).getMineEfficiency();
					final double effectiveMines = numberOfMines * efficiency;

					return miningCalculator.calculateMining(game, planet, effectiveMines);
				}
			}
		}
		return cargoProcessor.createCargoHolder();
	}

}