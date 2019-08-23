package com.pim.stars.effect.api.policies;

import java.util.Collection;
import java.util.stream.Collectors;

import com.pim.stars.effect.api.Effect;

public interface EffectCollectionProviderPolicy<T> extends EffectProviderPolicy<T> {

	public Collection<Effect> getEffectCollectionFromEffectHolder(T effectHolder);

	@Override
	@SuppressWarnings("unchecked") // because the compiler cannot know that the collection was filtered
	public default <E extends Effect> Collection<E> getEffectCollectionFromEffectHolder(final T effectHolder,
			final Class<E> effectClass) {
		return (Collection<E>) getEffectCollectionFromEffectHolder(effectHolder).stream()
				.filter(effect -> effectClass.isAssignableFrom(effect.getClass())).collect(Collectors.toList());
	}
}