package com.pim.stars.design.api;

import com.pim.stars.gadget.api.types.HullType;

public interface Design {

	public String getId();

	public String getName();

	public String getOwnerId();

	public HullType getHullType();
}
