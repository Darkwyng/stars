package com.pim.stars.colonization.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.planets.api.Planet;

public interface PlanetCapacityPolicy extends Effect {

	public int getPlanetCapacity(Planet planet, int capacity);
}
