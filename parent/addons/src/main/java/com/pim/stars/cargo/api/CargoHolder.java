package com.pim.stars.cargo.api;

import java.util.Collection;

import com.pim.stars.cargo.api.policies.CargoType;

public interface CargoHolder {

	public Collection<CargoItem> getItems();

	public int getQuantity(CargoType cargoType);

	public boolean isEmpty();

	public CargoTransferBuilder transferTo(CargoHolder target);

	public CargoTransferBuilder transferFrom(CargoHolder source);

	public CargoTransferBuilder transferToNowhere();

	public CargoTransferBuilder transferFromNowhere();

	public interface CargoTransferBuilder {

		public CargoTransferBuilder quantity(CargoType cargoType, int quantity);

		public CargoTransferBuilder item(CargoItem item);

		public CargoTransferBuilder all();

		public CargoTransferBuilder allOf(CargoHolder cargo);

		public CargoTransferResult execute();

		public CargoHolder sum();
	}

	public interface CargoTransferResult {

		public CargoHolder getSource();

		public CargoHolder getTarget();

		public Collection<CargoItem> getTransferredItems();
	}

	public interface CargoItem {

		public CargoType getType();

		public int getQuantity();
	}
}