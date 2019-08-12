package com.pim.stars.race.imp.traits;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.effect.api.Effect;

public abstract class AbstractRacialTrait {

	private Collection<Effect> effectCollection = new ArrayList<>();
	private String id;

	public Collection<Effect> getEffectCollection() {
		return effectCollection;
	}

	public void setEffectCollection(final Collection<Effect> effectCollection) {
		this.effectCollection = effectCollection;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
