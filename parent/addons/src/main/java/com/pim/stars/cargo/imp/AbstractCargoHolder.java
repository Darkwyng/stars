package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.util.Assert;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.policies.CargoType;

public abstract class AbstractCargoHolder implements CargoHolder {

	protected AbstractCargoHolder() {
		super();
	}

	@Override
	public abstract Collection<CargoItem> getItems();

	public abstract void commitChanges();

	@Override
	public boolean isEmpty() {
		return getItems().stream().findAny().isEmpty();
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
		return new CargoTransferBuilderImp(this, new CargoPool());
	}

	@Override
	public CargoTransferBuilder transferFromNowhere() {
		return new CargoTransferBuilderImp(new CargoPool(), this);
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
			addItem(new CargoItemImp(cargoType, quantity));
			return this;
		}

		@Override
		public CargoTransferBuilder item(final CargoItem item) {
			addItem(item);
			return this;
		}

		@Override
		public CargoTransferBuilder all() {
			source.getItems().stream().forEach(this::item);
			return this;
		}

		@Override
		public CargoTransferBuilder allOf(final CargoHolder cargo) {
			cargo.getItems().stream().forEach(this::item);
			return this;
		}

		protected void addItem(final CargoItem newItem) {
			// Remove item of same cargo type, so that only the new item will be considered:
			itemsToTranser.stream().filter(item -> item.getType().equals(newItem.getType())).findAny()
					.ifPresent(existingItem -> itemsToTranser.remove(existingItem));

			final int quantity = newItem.getQuantity();
			Assert.isTrue(quantity >= 0, "You cannot transfer a negative quantity " + quantity);

			if (quantity > 0) {
				itemsToTranser.add(newItem);
			}
		}

		@Override
		public CargoHolder sum() {
			return new CargoPool(itemsToTranser);
		}

		@Override
		public CargoTransferResult execute() {
			final AbstractCargoHolder sourceImp = (AbstractCargoHolder) source;
			final AbstractCargoHolder targetImp = (AbstractCargoHolder) target;

			for (final CargoItem item : itemsToTranser) {
				sourceImp.remove(item);
				targetImp.add(item);
			}
			sourceImp.commitChanges();
			targetImp.commitChanges();

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