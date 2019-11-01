package com.pim.stars.mineral.imp.effects;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.Cargo.CargoItem;
import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.mineral.api.effects.MiningPolicy;
import com.pim.stars.mineral.imp.reports.PlanetHasMinedReport;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.report.api.ReportCreator;

@Component
public class MineralGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetOwnerId planetOwnerId;
	@Autowired
	private PlanetName planetName;

	@Autowired
	private EffectCalculator effectCalculator;
	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ReportCreator reportCreator;

	@Override
	public void generateGame(final Game game, final GameGenerationContext gameGenerationContext) {
		for (final Planet planet : gamePlanetCollection.getValue(game)) {

			final CargoHolder totalMinedCargo = effectCalculator.calculateEffect(game, MiningPolicy.class, planet,
					cargoProcessor.createCargoHolder(), (policy, context, currentValue) -> {
						final CargoHolder minedCargo = policy.calculateMining(game, planet);
						return cargoProcessor.add(Arrays.asList(currentValue, minedCargo));
					});

			createReport(game, planet, totalMinedCargo);
			cargoProcessor.createCargoHolder(planet).transferFromNowhere().allOf(totalMinedCargo).execute();

		}
	}

	private void createReport(final Game game, final Planet planet, final CargoHolder totalMinedCargo) {
		final String raceId = planetOwnerId.getValue(planet);
		final String name = planetName.getValue(planet);
		final Integer totalQuantity = totalMinedCargo.getItems().stream()
				.collect(Collectors.summingInt(CargoItem::getQuantity));

		reportCreator.start(game, raceId).type(PlanetHasMinedReport.class).bundle(MineralConstants.REPORT_BUNDLE_NAME)
				.addArguments(name, totalQuantity.toString()).create();
	}
}
