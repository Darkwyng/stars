package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.util.Assert;

import com.pim.stars.cargo.api.Cargo.CargoItem;
import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.dataextension.api.Entity;

public class CargoHolderImp implements CargoHolder {

	private final Entity entity;
	private final Supplier<CargoDataExtensionPolicy> policySupplier;

	public CargoHolderImp(final Entity entity, final Supplier<CargoDataExtensionPolicy> cargoDataExtensionPolicy) {
		this.entity = entity;
		this.policySupplier = cargoDataExtensionPolicy;
	}

	@Override
	public Collection<CargoItem> getItems() {
		return policySupplier.get().getValue(entity).getItems();
	}

	@Override
	public int getQuantity(final CargoType cargoType) {
		return getCargoItemByType(cargoType).map(CargoItem::getQuantity).orElse(0);
	}

	@Override
	public CargoTransferBuilder transferTo(final CargoHolder target) {
		return new CargoTransferBuilderImp(this, target);
	}

	@Override
	public CargoTransferBuilder transferFrom(final CargoHolder source) {
		return new CargoTransferBuilderImp(source, this);
	}

	@Override
	public CargoTransferBuilder transferToNowhere() {
		return new CargoTransferBuilderImp(this, new InfinitePool());
	}

	@Override
	public CargoTransferBuilder transferFromNowhere() {
		return new CargoTransferBuilderImp(new InfinitePool(), this);
	}

	protected void remove(final CargoItem newItem) {
		final Optional<CargoItem> existingItem = getCargoItemByType(newItem.getType());
		if (existingItem.isPresent()) {
			final CargoItemImp cargoItem = (CargoItemImp) existingItem.get();
			cargoItem.removeQuantity(newItem.getQuantity());
		} else {
			// Nothing to remove, quantity stays at zero.
		}
	}

	protected void add(final CargoItem newItem) {
		final Optional<CargoItem> existingItem = getCargoItemByType(newItem.getType());
		if (existingItem.isPresent()) {
			final CargoItemImp cargoItem = (CargoItemImp) existingItem.get();
			cargoItem.addQuantity(newItem.getQuantity());
		} else {
			getItems().add(new CargoItemImp(newItem.getType(), newItem.getQuantity()));
		}
	}

	private Optional<CargoItem> getCargoItemByType(final CargoType cargoType) {
		return getItems().stream().filter(item -> cargoType.equals(item.getType())).findAny();
	}

	public static class CargoTransferBuilderImp implements CargoTransferBuilder {

		private final CargoHolder source;
		private final CargoHolder target;
		private final Collection<CargoItem> itemsToTranser = new ArrayList<>();

		public CargoTransferBuilderImp(final CargoHolder source, final CargoHolder target) {
			this.source = source;
			this.target = target;
		}

		@Override
		public CargoTransferBuilder quantity(final CargoType cargoType, final int quantity) {
			Assert.isTrue(quantity >= 0, "You cannot transfer a negative quantity " + quantity);
			if (quantity > 0) {
				addItem(new CargoItemImp(cargoType, quantity));
			}
			return this;
		}

		@Override
		public CargoTransferBuilder item(final CargoItem item) {
			addItem(item);
			return this;
		}

		protected void addItem(final CargoItem newItem) {
			// Remove item of same cargo type, so that only the new item will be considered:
			itemsToTranser.stream().filter(item -> item.getType().equals(newItem.getType())).findAny()
					.ifPresent(existingItem -> itemsToTranser.remove(existingItem));

			itemsToTranser.add(newItem);
		}

		@Override
		public CargoTransferResult execute() {
			final CargoHolderImp sourceImp = (CargoHolderImp) source;
			final CargoHolderImp targetImp = (CargoHolderImp) target;

			for (final CargoItem item : itemsToTranser) {
				sourceImp.remove(item);
				targetImp.add(item);
			}

			final Collection<CargoItem> transferredItems = itemsToTranser;
			return new CargoTransferResultImp(source, target, transferredItems);
		}
	}

	public static class CargoTransferResultImp implements CargoTransferResult {

		private final CargoHolder source;
		private final CargoHolder target;
		private final Collection<CargoItem> transferredItems;

		public CargoTransferResultImp(final CargoHolder source, final CargoHolder target,
				final Collection<CargoItem> transferredItems) {
			this.source = source;
			this.target = target;
			this.transferredItems = Collections.unmodifiableCollection(transferredItems);
		}

		@Override
		public CargoHolder getSource() {
			return source;
		}

		@Override
		public CargoHolder getTarget() {
			return target;
		}

		@Override
		public Collection<CargoItem> getTransferredItems() {
			return transferredItems;
		}
	}

	public static class CargoItemImp implements CargoItem {

		private final CargoType cargoType;
		private int quantity;

		public CargoItemImp(final CargoType cargoType, final int quantity) {
			this.cargoType = cargoType;
			this.quantity = quantity;
		}

		@Override
		public CargoType getType() {
			return cargoType;
		}

		@Override
		public int getQuantity() {
			return quantity;
		}

		protected void addQuantity(final int newQuantity) {
			quantity += newQuantity;
		}

		protected void removeQuantity(final int newQuantity) {
			quantity = Math.max(0, quantity - newQuantity);
		}
	}
}