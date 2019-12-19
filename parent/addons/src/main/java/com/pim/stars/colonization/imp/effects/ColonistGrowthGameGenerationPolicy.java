package com.pim.stars.colonization.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonistCalculator;
import com.pim.stars.colonization.api.ColonistCargoTypeProvider;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.planets.api.PlanetProvider;

@Component
public class ColonistGrowthGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private ColonistCalculator colonistCalculator;
	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ColonistCargoTypeProvider colonistCargoTypeProvider;

	@Override
	public int getSequence() {
		return 1400;
	}

	@Override
	public void generateGame(final GameGenerationContext generationContext) {
		final Game game = generationContext.getCurrentYear();
		planetProvider.getPlanetsByGame(game).forEach(planet -> {
			final int gain = colonistCalculator.getExpectedColonistGainForPlanet(game, planet);
			if (gain > 0) {
				cargoProcessor.createCargoHolder(game, planet).transferFromNowhere()
						.quantity(colonistCargoTypeProvider.getColonistCargoType(), gain).execute();
				// TODO: report for colonist growth
			}
		});
	}
}