package com.pim.stars.gadget.api.hull;

import java.util.Collection;

import com.pim.stars.gadget.api.types.GadgetType;

public interface GadgetSlot {

	public String getId();

	public int getMinimumNumberOfGadgets();

	public int getMaximumNumberOfGadgets();

	public Collection<GadgetType> getAllowedGadgetTypes();
}
