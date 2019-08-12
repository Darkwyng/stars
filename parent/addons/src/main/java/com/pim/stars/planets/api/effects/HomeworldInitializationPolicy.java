package com.pim.stars.planets.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.planets.api.Planet;

public interface HomeworldInitializationPolicy extends Effect {

	public void initializeHomeworld(Game game, Planet planet, GameInitializationData data);
}