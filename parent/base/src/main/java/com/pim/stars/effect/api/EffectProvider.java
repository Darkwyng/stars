package com.pim.stars.effect.api;

import java.util.Collection;

public interface EffectProvider {

	public <E extends Effect> Collection<E> getEffectCollection(final Object effectHolder, final Class<E> effectClass);
}
