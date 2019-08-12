package com.pim.stars.effect.api;

public interface EffectExecutor {

	public <E extends Effect> void executeEffect(Class<E> effectClass, final Object effectHolder,
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
