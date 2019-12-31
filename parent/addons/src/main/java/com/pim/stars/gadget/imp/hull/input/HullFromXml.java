package com.pim.stars.gadget.imp.hull.input;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.effect.api.Effect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HullFromXml {

	private Collection<Effect> effectCollection = new ArrayList<>();

	private String hullTypeId;

	private Collection<GadgetSlotFromXml> gadgetSlots = new ArrayList<>();
}
