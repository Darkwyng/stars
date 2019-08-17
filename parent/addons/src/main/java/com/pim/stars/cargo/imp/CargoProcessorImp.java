package com.pim.stars.cargo.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.dataextension.api.Entity;

public class CargoProcessorImp implements CargoProcessor {

	@Autowired(required = false)
	private final List<CargoDataExtensionPolicy<Entity<?>>> cargoDataExtensionPolicyList = new ArrayList<>();

	/** This map stores all policies for all entities. It is filled lazily when it is needed the first time. */
	private Map<Class<Entity<?>>, List<CargoDataExtensionPolicy<Entity<?>>>> policyMap;

	@Override
	public <T extends Entity<?>, S extends T> CargoHolder createCargoHolder(final S entity,
			final Class<T> entityClass) {
		return new CargoHolderImp(entity, new CargoDataExtensionPolicySupplier(entityClass));
	}

	/**
	 * This class will provide the {@link CargoDataExtensionPolicy} lazily, when it is needed to work on a
	 * {@link CargoHolder}.
	 */
	private final class CargoDataExtensionPolicySupplier implements Supplier<CargoDataExtensionPolicy<?>> {

		private final Class<?> entityClass;
		private CargoDataExtensionPolicy<?> policy;

		private CargoDataExtensionPolicySupplier(final Class<?> entityClass) {
			this.entityClass = entityClass;
		}

		@Override
		public CargoDataExtensionPolicy<?> get() {
			if (policy == null) {
				policy = getCargoDataExtensionPolicy(entityClass);
			}
			return policy;
		}

		private CargoDataExtensionPolicy<?> getCargoDataExtensionPolicy(final Class<?> entityClass) {
			final List<CargoDataExtensionPolicy<Entity<?>>> list = getPoliciesForEntityClass(entityClass);
			if (list == null || list.size() != 1) {
				final Integer listSize = list == null ? null : list.size();
				throw new IllegalStateException("An implementation of " + CargoDataExtensionPolicy.class.getName()
						+ "for entity " + entityClass.getName() + " is needed to call this service. " + listSize
						+ " were found.");
			} else {
				return list.iterator().next();
			}
		}

		private List<CargoDataExtensionPolicy<Entity<?>>> getPoliciesForEntityClass(final Class<?> entityClass) {
			if (policyMap == null) {
				fillPolicyMap();
			}
			return policyMap.get(entityClass);
		}

		private void fillPolicyMap() {
			synchronized (this) {
				if (policyMap == null) {
					policyMap = cargoDataExtensionPolicyList.stream()
							.collect(Collectors.groupingBy(CargoDataExtensionPolicy::getEntityClass));
				}
			}
		}
	}
}