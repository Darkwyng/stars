package com.pim.stars.colonization.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonistCalculator;
import com.pim.stars.colonization.api.policies.ColonistCargoType;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;

@Component
public class ColonistGrowthGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private ColonistCalculator colonistCalculator;
	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ColonistCargoType colonistCargoType;

	@Override
	public void generateGame(final Game game, final GameGenerationContext generationContext) {
		gamePlanetCollection.getValue(game).stream().forEach(planet -> {
			final int gain = colonistCalculator.getExpectedColonistGainForPlanet(game, planet);
			if (gain > 0) {
				cargoProcessor.createCargoHolder(planet).transferFromNowhere().quantity(colonistCargoType, gain)
						.execute();
				// TODO: report for colonist growth
			}
		});
	}
}