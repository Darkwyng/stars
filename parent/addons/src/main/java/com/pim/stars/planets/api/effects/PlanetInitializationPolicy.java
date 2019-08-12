package com.pim.stars.planets.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.planets.api.Planet;

public interface PlanetInitializationPolicy extends Effect {

	public void initializePlanet(Game game, Planet planet, GameInitializationData data);
}
