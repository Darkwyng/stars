package com.pim.stars.game.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;

public interface GameGenerationPolicy extends Effect {

	public void generateGame(GameGenerationContext context);

	public interface GameGenerationContext {

		public Game getPreviousYear();

		public Game getCurrentYear();

		public Object get(String key);

		public void set(String key, Object value);
	}
}
