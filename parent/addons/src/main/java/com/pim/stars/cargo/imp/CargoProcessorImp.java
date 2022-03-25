package com.pim.stars.cargo.imp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoHolder.CargoItem;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.imp.AbstractCargoHolder.CargoItemImp;
import com.pim.stars.cargo.imp.persistence.CargoPersistenceInterface;
import com.pim.stars.game.api.Game;

@Component
public class CargoProcessorImp implements CargoProcessor {

	@Autowired
	private CargoPersistenceInterface cargoPersistenceInterface;

	@Override
	public CargoHolder createCargoHolder(final Game game, final Object entity) {
		final Supplier<Collection<CargoItem>> supplier = () -> cargoPersistenceInterface.loadItemsForObject(game, entity);
		final Consumer<Collection<CargoItem>> consumer = items -> cargoPersistenceInterface.persist(game, entity,
				items);
		return new EntityWrappingCargoHolder(supplier, consumer);
	}

	@Override
	public CargoHolder createCargoHolder() {
		return new CargoPool();
	}

	@Override
	public CargoHolder add(final Collection<CargoHolder> cargoHolders) {
		final Map<CargoType, Integer> map = new HashMap<>();
		for (final CargoHolder cargoHolder : cargoHolders) {
			cargoHolder.getItems().stream().forEach(item -> {
				final Integer current = map.get(item.getType());
				final int newQuantity = (current == null) ? item.getQuantity() : current + item.getQuantity();
				map.put(item.getType(), newQuantity);
			});
		}

		final CargoPool result = new CargoPool();
		for (final Entry<CargoType, Integer> entry : map.entrySet()) {
			result.getItems().add(new CargoItemImp(entry.getKey(), entry.getValue()));
		}

		return result;
	}
}