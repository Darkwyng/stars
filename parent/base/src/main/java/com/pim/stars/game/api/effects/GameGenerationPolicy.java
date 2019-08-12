package com.pim.stars.game.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;

public interface GameGenerationPolicy extends Effect {

	public void generateGame(Game game);
}
