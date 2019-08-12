package com.pim.stars.colonization.imp.effects;

import com.pim.stars.colonization.api.effects.PlanetCapacityPolicy;
import com.pim.stars.planets.api.Planet;

public class FactorPlanetCapacityPolicy implements PlanetCapacityPolicy {

	private double factor;

	@Override
	public int getPlanetCapacity(final Planet planet, final int capacity) {
		return (int) Math.round(factor * capacity);
	}

	/** This setter is called via reflection, when the effect is created from XML. */
	public void setFactor(final double factor) {
		this.factor = factor;
	}
}