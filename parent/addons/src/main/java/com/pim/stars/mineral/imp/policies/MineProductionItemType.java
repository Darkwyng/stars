package com.pim.stars.mineral.imp.policies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.imp.effects.MineralConstants;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;
import com.pim.stars.mineral.imp.persistence.race.MineralRaceRepository;
import com.pim.stars.mineral.imp.reports.PlanetHasBuiltMinesReport;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.report.api.ReportCreator;
import com.pim.stars.resource.api.ResourceProductionCostTypeProvider;

@Component
public class MineProductionItemType implements ProductionItemType {

	@Autowired
	private ResourceProductionCostTypeProvider resourceProductionCostTypeProvider;
	@Autowired
	private MineralRaceRepository mineralRaceRepository;
	@Autowired
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;
	@Autowired
	private ReportCreator reportCreator;

	@Override
	public ProductionCost getCostPerItem(final Game game, final Planet planet, final ProductionCostBuilder builder) {
		final String ownerId = getOwnerId(planet);
		final int mineProductionCost = mineralRaceRepository.findByRaceId(ownerId).getMineProductionCost();

		return builder.add(resourceProductionCostTypeProvider.getResourceProductionCostType(), mineProductionCost)
				.build();
	}

	@Override
	public void produce(final Game game, final Planet planet, final int numberOfItems) {
		mineralPlanetPersistenceInterface.buildMines(game, planet, numberOfItems);
		createReport(game, planet, numberOfItems);
	}

	private void createReport(final Game game, final Planet planet, final int numberOfItems) {
		final String raceId = getOwnerId(planet);

		reportCreator.start(game, raceId).type(PlanetHasBuiltMinesReport.class)
				.bundle(MineralConstants.REPORT_BUNDLE_NAME)
				.addArguments(planet.getName(), String.valueOf(numberOfItems)).create();
	}

	private String getOwnerId(final Planet planet) {
		return planet.getOwnerId()
				.orElseThrow(() -> new IllegalStateException(planet.getName() + " must have an owner to build mines."));
	}
}