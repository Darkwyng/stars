package com.pim.stars.mineral.imp.effects;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.mineral.api.effects.MiningPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;

@Component
public class MineralGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private GamePlanetCollection gamePlanetCollection;

	@Autowired
	private EffectCalculator effectCalculator;
	@Autowired
	private CargoProcessor cargoProcessor;

	@Override
	public void generateGame(final Game game, final GameGenerationContext gameGenerationContext) {
		for (final Planet planet : gamePlanetCollection.getValue(game)) {

			final CargoHolder totalMinedCargo = effectCalculator.calculateEffect(game, MiningPolicy.class, planet,
					cargoProcessor.createCargoHolder(), (policy, context, currentValue) -> {
						final CargoHolder minedCargo = policy.calculateMining(game, planet);
						return cargoProcessor.add(Arrays.asList(currentValue, minedCargo));
					});
			// TODO: report for mining
			cargoProcessor.createCargoHolder(planet).transferFromNowhere().allOf(totalMinedCargo).execute();
		}
	}
}
