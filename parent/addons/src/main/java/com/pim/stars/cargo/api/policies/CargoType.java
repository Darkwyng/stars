package com.pim.stars.cargo.api.policies;

import java.util.Collection;

public interface CargoType {

	public String getId();

	public interface CargoTypeFactory {

		public Collection<CargoType> createCargoTypes();
	}
}