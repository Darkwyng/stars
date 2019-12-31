package com.pim.stars.gadget.imp.gadget.input;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.effect.api.Effect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GadgetFromXml {

	private Collection<Effect> effectCollection = new ArrayList<>();

	private String gadgetTypeId;
}
