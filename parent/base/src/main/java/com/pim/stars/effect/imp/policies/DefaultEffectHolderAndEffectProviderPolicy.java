package com.pim.stars.effect.imp.policies;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.policies.EffectHolderProviderPolicy;
import com.pim.stars.effect.api.policies.EffectProviderPolicy;

/**
 * This implementation will load the effects that are within the application context.
 */
public class DefaultEffectHolderAndEffectProviderPolicy
		implements EffectProviderPolicy<Object>, EffectHolderProviderPolicy {

	private final Object EFFECT_HOLDER = this;

	@Autowired
	private ApplicationContext applicationContext;

	private final Map<Class<?>, Collection<Effect>> map = new HashMap<>();

	@Override
	public boolean matchesInitialEffectHolder(final Object effectHolder) {
		return true; // Since this returns true, EffectProvider will call...
	}

	@Override
	public Collection<Object> getFurtherEffectHolders(final Object effectHolder) {
		return Collections.singleton(EFFECT_HOLDER); // ... this method. Its result will be then passed into...
	}

	@Override
	public boolean matchesEffectHolder(final Object effectHolder) {
		return (effectHolder == EFFECT_HOLDER); // .. this method, which will return true (for EFFECT_HOLDER), so that...
	}

	@Override
	public <E extends Effect> Collection<E> getEffectCollectionFromEffectHolder(final Object effectHolder,
			final Class<E> effectClass) {
		return getEffectCollectionByEffectClass(effectClass); // ... the effects will be loaded from the application context here.
	}

	@SuppressWarnings("unchecked")
	private <E extends Effect> Collection<E> getEffectCollectionByEffectClass(final Class<E> effectClass) {
		Collection<Effect> result = map.get(effectClass);
		if (result == null) {
			synchronized (this) {
				if (result == null) {
					fillMapForEffectClass(effectClass);
				}
			}
			result = map.get(effectClass);
		}
		return (Collection<E>) result;
	}

	private void fillMapForEffectClass(final Class<? extends Effect> effectClass) {
		final List<Effect> list = applicationContext.getBeansOfType(Effect.class).values().stream()
				.filter(effect -> effectClass.isAssignableFrom(effect.getClass())).collect(Collectors.toList());
		map.put(effectClass, list);
	}
}