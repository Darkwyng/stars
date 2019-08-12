package com.pim.stars.cargo.api;

import java.util.Collection;

import com.pim.stars.cargo.api.Cargo.CargoItem;
import com.pim.stars.cargo.api.policies.CargoType;

public interface CargoHolder {

	public Collection<CargoItem> getItems();

	public int getQuantity(CargoType cargoType);

	public CargoTransferBuilder transferTo(CargoHolder target);

	public CargoTransferBuilder transferFrom(CargoHolder source);

	public CargoTransferBuilder transferToNowhere();

	public CargoTransferBuilder transferFromNowhere();

	public interface CargoTransferBuilder {

		public CargoTransferBuilder quantity(CargoType cargoType, int quantity);

		public CargoTransferBuilder item(CargoItem item);

		public CargoTransferResult execute();
	}

	public interface CargoTransferResult {

		public CargoHolder getSource();

		public CargoHolder getTarget();

		public Collection<CargoItem> getTransferredItems();
	}
}