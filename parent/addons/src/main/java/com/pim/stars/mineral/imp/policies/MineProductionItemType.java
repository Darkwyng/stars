package com.pim.stars.mineral.imp.policies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.api.extensions.RaceMiningSettings;
import com.pim.stars.mineral.api.extensions.RaceMiningSettings.MiningSettings;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.resource.api.policies.ResourceProductionCostType;
import com.pim.stars.turn.api.Race;

@Component
public class MineProductionItemType implements ProductionItemType {

	@Autowired
	private ResourceProductionCostType resourceProductionCostType;
	@Autowired
	private PlanetOwnerId planetOwnerId;
	@Autowired
	private GameRaceCollection gameRaceCollection;
	@Autowired
	private RaceId raceId;
	@Autowired
	private RaceMiningSettings raceMiningSettings;

	@Autowired
	private PlanetMineCount planetMineCount;

	@Override
	public ProductionCost getCostPerItem(final Game game, final Planet planet, final ProductionCostBuilder builder) {
		final String ownerId = planetOwnerId.getValue(planet);
		final Race owner = gameRaceCollection.getValue(game).stream()
				.filter(race -> raceId.getValue(race).equals(ownerId)).findAny().get();
		final MiningSettings miningSettings = raceMiningSettings.getValue(owner);

		return builder.add(resourceProductionCostType, miningSettings.getMineProductionCost()).build();
	}

	@Override
	public void produce(final Game game, final Planet planet, final int numberOfItems) {
		final var oldValue = planetMineCount.getValue(planet);
		final var newValue = oldValue + numberOfItems;
		planetMineCount.setValue(planet, newValue);
	}
}