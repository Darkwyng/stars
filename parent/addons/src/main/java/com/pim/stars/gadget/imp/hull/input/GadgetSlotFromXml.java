package com.pim.stars.gadget.imp.hull.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GadgetSlotFromXml {

	private String id;
	private String allowedNumberRange;
	private String allowedGadgetTypeIds;
}
