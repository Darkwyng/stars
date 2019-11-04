package com.pim.stars.colonization.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.colonization.api.effects.PlanetCapacityPolicy;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.planets.api.Planet;

@Component
public class DefaultPlanetCapacityPolicy implements PlanetCapacityPolicy {

	@Autowired
	private ColonizationProperties colonizationProperties;

	@Override
	public int getPlanetCapacity(final Planet planet, final int capacity) {
		return colonizationProperties.getDefaultPlanetCapacity();
	}
}