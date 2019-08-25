package com.pim.stars.resource.api.policies;

import org.springframework.stereotype.Component;

import com.pim.stars.production.api.policies.ProductionCostType;

@Component
public class ResourceProductionCostType implements ProductionCostType {

	@Override
	public String getId() {
		return "R";
	}
}