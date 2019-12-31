package com.pim.stars.gadget.imp.types;

import com.pim.stars.gadget.api.types.HullType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class HullTypeImp implements HullType {

	private final String id;
}
