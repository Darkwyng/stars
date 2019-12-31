package com.pim.stars.gadget.api.hull;

import java.util.Collection;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.gadget.api.types.HullType;

public interface Hull {

	public String getId();

	public HullType getHullType();

	public Collection<GadgetSlot> getGadgetSlots();

	public Collection<Effect> getEffectCollection();
}
