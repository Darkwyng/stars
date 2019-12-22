package com.pim.stars.effect.api.policies;

import java.util.Collection;
import java.util.stream.Collectors;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;

public interface EffectCollectionProviderPolicy<T> extends EffectProviderPolicy<T> {

	public Collection<Effect> getEffectCollectionFromEffectHolder(Game game, T effectHolder);

	@Override
	@SuppressWarnings("unchecked") // because the compiler cannot know that the collection was filtered
	public default <E extends Effect> Collection<E> getEffectCollectionFromEffectHolder(Game game,
			final T effectHolder, final Class<E> effectClass) {
		return (Collection<E>) getEffectCollectionFromEffectHolder(game, effectHolder).stream()
				.filter(effect -> effectClass.isAssignableFrom(effect.getClass())).collect(Collectors.toList());
	}
}