package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.cargo.api.Cargo.CargoItem;

public class InfinitePool extends CargoHolderImp {

	public InfinitePool() {
		super(null, null);
	}

	@Override
	public Collection<CargoItem> getItems() {
		return new ArrayList<>();
	}
}
