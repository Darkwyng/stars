package com.pim.stars.colonization.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonistCalculator;
import com.pim.stars.colonization.api.ColonistCargoTypeProvider;
import com.pim.stars.colonization.api.effects.PlanetCapacityPolicy;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

@Component
public class ColonistCalculatorImp implements ColonistCalculator {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ColonistCargoTypeProvider colonistCargoTypeProvider;

	@Autowired
	private EffectCalculator effectCalculator;

	@Override
	public int getCurrentPlanetPopulation(final Game game, final Planet planet) {
		if (planet.getOwnerId().isEmpty()) {
			return 0;
		} else {
			return cargoProcessor.createCargoHolder(game, planet)
					.getQuantity(colonistCargoTypeProvider.getColonistCargoType());
		}
	}

	@Override
	public int getPlanetCapacity(final Game game, final Planet planet) {
		if (planet.getOwnerId().isEmpty()) {
			return 0;
		} else {
			return effectCalculator.calculateEffect(game, PlanetCapacityPolicy.class, planet, 0,
					(policy, context, currentValue) -> policy.getPlanetCapacity(planet, currentValue));
		}
	}

	@Override
	public double getMaximumGrowthRateForPlanet(final Game game, final Planet planet) {
		if (planet.getOwnerId().isEmpty()) {
			return 0;
		} else {
			return 0.15;
		}
	}

	@Override
	public int getExpectedColonistGainForPlanet(final Game game, final Planet planet) {
		if (planet.getOwnerId().isEmpty()) {
			return 0;
		} else {
			final CargoHolder planetCargoHolder = cargoProcessor.createCargoHolder(game, planet);

			final int currentPopulation = planetCargoHolder
					.getQuantity(colonistCargoTypeProvider.getColonistCargoType());
			final double maximumGrowthRate = getMaximumGrowthRateForPlanet(game, planet);
			final int capacity = getPlanetCapacity(game, planet);

			final double currentGrowthRate;
			if (currentPopulation <= 0.25 * capacity) {
				currentGrowthRate = maximumGrowthRate;
			} else {
				currentGrowthRate = maximumGrowthRate * (capacity - currentPopulation) / (0.75 * capacity);
			}

			return (int) Math.floor(currentPopulation * currentGrowthRate);
		}
	}
}