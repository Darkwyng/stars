package com.pim.stars.gadget.api;

import java.util.Collection;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.gadget.api.types.GadgetType;

public interface Gadget {

	public String getId();

	public GadgetType getGadgetType();

	public Collection<Effect> getEffectCollection();
}
