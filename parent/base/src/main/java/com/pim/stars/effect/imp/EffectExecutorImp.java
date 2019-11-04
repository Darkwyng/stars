package com.pim.stars.effect.imp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.game.api.Game;

@Component
public class EffectExecutorImp implements EffectExecutor {

	@Autowired
	private EffectProvider effectProvider;

	public class EffectContextImp implements EffectContext {

		private final Map<String, Object> data = new HashMap<>();

		@Override
		public Object get(final String key) {
			return data.get(key);
		}

		@Override
		public void set(final String key, final Object value) {
			data.put(key, value);
		}
	}

	@Override
	public <E extends Effect> void executeEffect(final Game game, final Class<E> effectClass, final Object effectHolder,
			final EffectFunction<E> function) {
		final Collection<E> effectCollection = effectProvider.getEffectCollection(game, effectHolder, effectClass);
		final EffectContext context = new EffectContextImp();

		for (final E effect : effectCollection) {
			function.execute(effect, context);
		}
	}
}