package com.pim.stars.gadget.imp.hull;

import java.util.Collection;

import com.pim.stars.gadget.api.hull.GadgetSlot;
import com.pim.stars.gadget.api.types.GadgetType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class GadgetSlotImp implements GadgetSlot {

	private final String slotId;

	private final int minimumNumberOfGadgets;
	private final int maximumNumberOfGadgets;

	private final Collection<GadgetType> allowedGadgetTypes;
}
