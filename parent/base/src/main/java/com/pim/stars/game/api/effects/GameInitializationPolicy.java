package com.pim.stars.game.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;

public interface GameInitializationPolicy extends Effect {

	public void initializeGame(Game game, GameInitializationData initializationData);
}
