package com.pim.stars.effect.api;

import java.util.Collection;
import java.util.Collections;

public interface Effect {

	public default int getSequence() {
		return 1000; // TODO: use sequences
	}

	public default Collection<Class<? extends Effect>> getDeactivatedEffects() {
		return Collections.emptyList();
	}
}
