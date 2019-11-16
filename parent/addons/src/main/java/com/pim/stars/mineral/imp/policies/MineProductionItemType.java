package com.pim.stars.mineral.imp.policies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.extensions.PlanetMineCount;
import com.pim.stars.mineral.imp.effects.MineralConstants;
import com.pim.stars.mineral.imp.persistence.MineralRaceRepository;
import com.pim.stars.mineral.imp.reports.PlanetHasBuiltMinesReport;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.report.api.ReportCreator;
import com.pim.stars.resource.api.policies.ResourceProductionCostType;

@Component
public class MineProductionItemType implements ProductionItemType {

	@Autowired
	private ResourceProductionCostType resourceProductionCostType;
	@Autowired
	private PlanetOwnerId planetOwnerId;
	@Autowired
	private MineralRaceRepository mineralRaceRepository;
	@Autowired
	private PlanetMineCount planetMineCount;
	@Autowired
	private PlanetName planetName;
	@Autowired
	private ReportCreator reportCreator;

	@Override
	public ProductionCost getCostPerItem(final Game game, final Planet planet, final ProductionCostBuilder builder) {
		final String ownerId = planetOwnerId.getValue(planet);
		final int mineProductionCost = mineralRaceRepository.findByRaceId(ownerId).getMineProductionCost();

		return builder.add(resourceProductionCostType, mineProductionCost).build();
	}

	@Override
	public void produce(final Game game, final Planet planet, final int numberOfItems) {
		final var oldValue = planetMineCount.getValue(planet);
		final var newValue = oldValue + numberOfItems;
		planetMineCount.setValue(planet, newValue);

		createReport(game, planet, numberOfItems);
	}

	private void createReport(final Game game, final Planet planet, final int numberOfItems) {
		final String raceId = planetOwnerId.getValue(planet);
		final String name = planetName.getValue(planet);

		reportCreator.start(game, raceId).type(PlanetHasBuiltMinesReport.class)
				.bundle(MineralConstants.REPORT_BUNDLE_NAME).addArguments(name, String.valueOf(numberOfItems)).create();
	}
}