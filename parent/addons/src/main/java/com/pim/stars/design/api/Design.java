package com.pim.stars.design.api;

import com.pim.stars.design.api.types.DesignType;

public interface Design {

	public String getId();

	public String getName();

	public String getOwnerId();

	public DesignType getDesignType();
}
