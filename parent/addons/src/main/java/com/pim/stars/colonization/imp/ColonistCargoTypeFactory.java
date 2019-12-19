package com.pim.stars.colonization.imp;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.api.policies.CargoType.CargoTypeFactory;
import com.pim.stars.colonization.api.ColonistCargoTypeProvider;

@Component
public class ColonistCargoTypeFactory implements CargoTypeFactory, ColonistCargoTypeProvider {

	private final CargoType colonistCargoType = new ColonistCargoType();

	@Override
	public Collection<CargoType> createCargoTypes() {
		return Arrays.asList(colonistCargoType);
	}

	@Override
	public CargoType getColonistCargoType() {
		return colonistCargoType;
	}

	private class ColonistCargoType implements CargoType {

		@Override
		public String getId() {
			return "C";
		}
	}
}
