package com.pim.stars.mineral.imp.effects;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoHolder.CargoItem;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.mineral.api.effects.MiningPolicy;
import com.pim.stars.mineral.imp.reports.PlanetHasMinedReport;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.report.api.ReportCreator;

@Component
public class MineralGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private EffectCalculator effectCalculator;
	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ReportCreator reportCreator;

	@Override
	public int getSequence() {
		return 1100;
	}

	@Override
	public void generateGame(final GameGenerationContext generationContext) {
		final Game game = generationContext.getCurrentYear();
		planetProvider.getPlanetsByGame(game).forEach(planet -> {
			final CargoHolder totalMinedCargo = effectCalculator.calculateEffect(game, MiningPolicy.class, planet,
					cargoProcessor.createCargoHolder(), (policy, context, currentValue) -> {
						final CargoHolder minedCargo = policy.calculateMining(game, planet);
						return cargoProcessor.add(Arrays.asList(currentValue, minedCargo));
					});

			if (!totalMinedCargo.isEmpty()) {
				createReportIfNecessary(game, planet, totalMinedCargo);
				cargoProcessor.createCargoHolder(game, planet).transferFromNowhere().allOf(totalMinedCargo).execute();
			}
		});
	}

	private void createReportIfNecessary(final Game game, final Planet planet, final CargoHolder totalMinedCargo) {
		final Optional<String> ownerId = planet.getOwnerId();
		if (ownerId.isPresent()) {
			final String raceId = ownerId.get();
			final Integer totalQuantity = totalMinedCargo.getItems().stream()
					.collect(Collectors.summingInt(CargoItem::getQuantity));
			reportCreator.start(game, raceId).type(PlanetHasMinedReport.class)
					.bundle(MineralConstants.REPORT_BUNDLE_NAME)
					.addArguments(planet.getName(), totalQuantity.toString()).create();
		}
	}
}
