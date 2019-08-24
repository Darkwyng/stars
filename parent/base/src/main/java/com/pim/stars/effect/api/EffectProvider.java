package com.pim.stars.effect.api;

import java.util.Collection;

import com.pim.stars.game.api.Game;

public interface EffectProvider {

	public <E extends Effect> Collection<E> getEffectCollection(Game game, Object effectHolder, Class<E> effectClass);
}
