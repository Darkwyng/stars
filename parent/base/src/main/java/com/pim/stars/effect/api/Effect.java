package com.pim.stars.effect.api;

import java.util.Collection;
import java.util.Collections;

public interface Effect {

	public default int getSequence() {
		return 0;
	}

	public default Collection<Class<? extends Effect>> getDeactivatedEffects() {
		return Collections.emptyList();
	}
}
