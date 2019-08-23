package com.pim.stars.colonization.imp;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonistCalculator;
import com.pim.stars.colonization.api.effects.PlanetCapacityPolicy;
import com.pim.stars.colonization.api.policies.ColonistCargoType;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;

public class ColonistCalculatorImp implements ColonistCalculator {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ColonistCargoType colonistCargoType;

	@Autowired
	private PlanetOwnerId planetOwnerId;

	@Autowired
	private EffectCalculator effectCalculator;

	@Override
	public int getCurrentPlanetPopulation(final Planet planet) {
		if (planetOwnerId.getValue(planet) == null) {
			return 0;
		} else {
			return cargoProcessor.createCargoHolder(planet, Planet.class).getQuantity(colonistCargoType);
		}
	}

	@Override
	public int getPlanetCapacity(final Planet planet) {
		if (planetOwnerId.getValue(planet) == null) {
			return 0;
		} else {
			return effectCalculator.calculateEffect(PlanetCapacityPolicy.class, planet, 0,
					(policy, context, currentValue) -> policy.getPlanetCapacity(planet, currentValue));
		}
	}

	@Override
	public double getMaximumGrowthRateForPlanet(final Planet planet) {
		if (planetOwnerId.getValue(planet) == null) {
			return 0;
		} else {
			return 0.17; // TODO: depends on race and habitability
		}
	}

	@Override
	public int getExpectedColonistGainForPlanet(final Planet planet) {
		if (planetOwnerId.getValue(planet) == null) {
			return 0;
		} else {
			final CargoHolder planetCargoHolder = cargoProcessor.createCargoHolder(planet, Planet.class);

			final int currentPopulation = planetCargoHolder.getQuantity(colonistCargoType);
			final double maximumGrowthRate = getMaximumGrowthRateForPlanet(planet);
			final int capacity = getPlanetCapacity(planet);

			final double currentGrowthRate;
			if (currentPopulation <= 0.25 * capacity) {
				currentGrowthRate = maximumGrowthRate;
			} else {
				currentGrowthRate = maximumGrowthRate * (capacity - currentPopulation) / (0.75 * capacity); // TODO: something like that?
			}

			return (int) Math.floor(currentPopulation * currentGrowthRate);
		}
	}
}