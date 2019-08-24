package com.pim.stars.effect.api.policies;

import java.util.Collection;

import com.pim.stars.game.api.Game;

public interface EffectHolderProviderPolicy<T> {

	public boolean matchesInitialEffectHolder(Object effectHolder);

	public Collection<Object> getFurtherEffectHolders(Game game, T effectHolder);
}
