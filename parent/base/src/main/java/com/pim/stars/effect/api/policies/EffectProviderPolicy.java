package com.pim.stars.effect.api.policies;

import java.util.Collection;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;

public interface EffectProviderPolicy<T> {

	public boolean matchesEffectHolder(Object effectHolder);

	public <E extends Effect> Collection<E> getEffectCollectionFromEffectHolder(Game game, T effectHolder, Class<E> effectClass);
}
