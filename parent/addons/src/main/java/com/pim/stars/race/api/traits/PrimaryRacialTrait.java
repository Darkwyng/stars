package com.pim.stars.race.api.traits;

import java.util.Collection;

import com.pim.stars.effect.api.Effect;

public interface PrimaryRacialTrait {

	public Collection<Effect> getEffectCollection();

	public String getId();
}
