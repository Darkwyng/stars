package com.pim.stars.effect.api.policies;

import java.util.Collection;

public interface EffectHolderProviderPolicy {

	public boolean matchesInitialEffectHolder(Object effectHolder);

	public Collection<Object> getFurtherEffectHolders(Object effectHolder);
}
