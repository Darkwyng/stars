package com.pim.stars.colonization.api.policies;

import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.policies.CargoType;

@Component
public class ColonistCargoType implements CargoType {

	@Override
	public String getId() {
		return "C";
	}
}
