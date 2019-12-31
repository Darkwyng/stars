package com.pim.stars.gadget.imp.types;

import com.pim.stars.gadget.api.types.GadgetType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class GadgetTypeImp implements GadgetType {

	private final String id;
}
