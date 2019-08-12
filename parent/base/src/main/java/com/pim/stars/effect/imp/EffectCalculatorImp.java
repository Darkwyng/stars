package com.pim.stars.effect.imp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectProvider;

public class EffectCalculatorImp implements EffectCalculator {

	@Autowired
	private EffectProvider effectProvider;

	@Override
	public <E extends Effect, R> R calculateEffect(final Class<E> effectClass, final Object effectHolder,
			final R defaultValue, final EffectFunction<E, R> function) {

		final Collection<E> effectCollection = effectProvider.getEffectCollection(effectHolder, effectClass);
		final EffectContext context = new EffectContextImp();

		R currentValue = defaultValue;
		for (final E effect : effectCollection) {
			currentValue = function.calculate(effect, context, currentValue);
		}
		return currentValue;
	}

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
}