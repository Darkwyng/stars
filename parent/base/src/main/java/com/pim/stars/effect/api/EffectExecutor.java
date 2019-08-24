package com.pim.stars.effect.api;

import com.pim.stars.game.api.Game;

public interface EffectExecutor {

	public <E extends Effect> void executeEffect(Game game, Class<E> effectClass, Object effectHolder,
			EffectFunction<E> function);

	@FunctionalInterface
	public interface EffectFunction<E extends Effect> {

		public void execute(E effect, EffectContext context);
	}

	public interface EffectContext {

		public Object get(String key);

		public void set(String key, Object value);
	}
}
