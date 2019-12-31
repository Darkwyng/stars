package com.pim.stars.gadget.imp.gadget;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.gadget.api.Gadget;
import com.pim.stars.gadget.api.types.GadgetType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class GadgetImp implements Gadget {

	private final String id;
	private Collection<Effect> effectCollection = new ArrayList<>();

	private final GadgetType gadgetType;
}