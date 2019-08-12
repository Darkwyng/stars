package com.pim.stars.effect.api;

public interface EffectCalculator {

	public <E extends Effect, R> R calculateEffect(Class<E> effectClass, final Object effectHolder, R defaultValue,
			EffectFunction<E, R> function);

	@FunctionalInterface
	public interface EffectFunction<E extends Effect, R> {

		public R calculate(E effect, EffectContext context, R currentValue);
	}

	public interface EffectContext {

		public Object get(String key);

		public void set(String key, Object value);
	}
}
