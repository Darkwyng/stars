package com.pim.stars.resource.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;

public interface ResourceCalculationPolicy extends Effect {

	public int getPlanetResources(Game game, Planet planet, int resources);
}
