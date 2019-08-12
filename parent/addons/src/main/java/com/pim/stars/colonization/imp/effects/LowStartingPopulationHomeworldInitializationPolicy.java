package com.pim.stars.colonization.imp.effects;

import java.util.Collection;
import java.util.Collections;

import com.pim.stars.effect.api.Effect;

public class LowStartingPopulationHomeworldInitializationPolicy extends ColonistHomeworldInitializationPolicy {

	private int initialPopulation;

	@Override
	public int getInitialPopulation() {
		return initialPopulation;
	}

	/** This setter is called via reflection, when the effect is created from XML. */
	public void setInitialPopulation(final int initialPopulation) {
		this.initialPopulation = initialPopulation;
	}

	@Override
	public Collection<Class<? extends Effect>> getDeactivatedEffects() {
		return Collections.singleton(ColonistHomeworldInitializationPolicy.class);
	}
}
