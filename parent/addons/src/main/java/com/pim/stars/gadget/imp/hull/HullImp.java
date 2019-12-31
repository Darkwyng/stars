package com.pim.stars.gadget.imp.hull;

import java.util.Collection;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.gadget.api.hull.GadgetSlot;
import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.gadget.api.types.HullType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class HullImp implements Hull {

	private final String id;
	private final Collection<Effect> effectCollection;

	private final HullType hullType;

	private final Collection<GadgetSlot> gadgetSlots;
}
