package com.pim.stars.race.imp.traits;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.effect.api.Effect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractRacialTrait {

	private Collection<Effect> effectCollection = new ArrayList<>();
	private String id;
}
