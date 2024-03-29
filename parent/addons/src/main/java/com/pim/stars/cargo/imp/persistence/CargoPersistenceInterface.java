package com.pim.stars.cargo.imp.persistence;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoHolder.CargoItem;
import com.pim.stars.cargo.api.CargoItemProvider;
import com.pim.stars.cargo.api.policies.CargoHolderDefinition;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.imp.AbstractCargoHolder.CargoItemImp;
import com.pim.stars.cargo.imp.EntityWrappingCargoHolder;
import com.pim.stars.game.api.Game;

@Component
public class CargoPersistenceInterface implements CargoItemProvider {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired(required = false)
	private final Collection<CargoHolderDefinition<?>> cargoHolderDefinitions = new ArrayList<>();

	@Autowired(required = false)
	private final Collection<CargoType> cargoTypes = new ArrayList<>();

	private Map<String, CargoType> cargoTypeByIdMap = null;

	/** Write data to the database. */
	public void persist(final Game game, final Object cargoHolder, final Collection<CargoItem> items) {
		final CargoEntity entity = initializeEntity(game, cargoHolder, items);

		cargoRepository.save(entity); // will insert a new entry or update an existing one
	}

	/** Read data from the database */
	public Collection<CargoItem> loadItemsForObject(final Game game, final Object cargoHolder) {
		final CargoEntityId entityId = createEntityId(game, cargoHolder);
		final Optional<CargoEntity> optionalEntity = cargoRepository.findById(entityId);

		return optionalEntity.map(this::mapEntityToItems).orElse(Collections.emptyList());
	}

	/** Read data from the database */
	@Override
	public Map<String, CargoHolder> getItemsForCargoHolderType(final Game game,
			final CargoHolderDefinition<? extends Object> cargoHolderDefinition) {

		return cargoRepository
				.findByGameIdAndYearAndType(game.getId(), game.getYear(), cargoHolderDefinition.getCargoHolderType())
				.stream() //
				.collect(Collectors.toMap( //
						entity -> entity.getEntityId().getCargoHolderId(), //
						entity -> {
							final Supplier<Collection<CargoItem>> supplier = () -> mapEntityToItems(entity);
							final Consumer<Collection<CargoItem>> consumer = items -> persist(game, entity, items);
							return new EntityWrappingCargoHolder(supplier, consumer);
						}));
	}

	private List<CargoItem> mapEntityToItems(final CargoEntity entity) {
		return entity.getItems().stream().map(this::initializeCargoItem).collect(toList());
	}

	/**
	 * Copy all persistent entities to the current year, so that historic data remains untouched by the game generation,
	 * that is starting, and so that loading items ({@link #loadItems()} finds the data also for the current year.
	 */
	public void cloneEntitiesOfPreviousYear(final Game previousYear, final Game currentYear) {

		final List<CargoEntity> modifiedEntities = cargoRepository
				.findByGameIdAndYear(previousYear.getId(), previousYear.getYear()).stream().peek(entity -> {
					updateEntityIdForCurrentYear(entity, currentYear);
				}).collect(Collectors.toList());

		cargoRepository.saveAll(modifiedEntities); // will insert new entries, because the IDs have changed
	}

	private CargoItem initializeCargoItem(final CargoEntityItem item) {
		return new CargoItemImp(getCargoTypeByEntity(item), item.getQuantity());
	}

	private CargoEntity initializeEntity(final Game game, final Object cargoHolder, final Collection<CargoItem> items) {
		final CargoEntity entity = new CargoEntity();
		final CargoEntityId entityId = createEntityId(game, cargoHolder);
		entity.setEntityId(entityId);

		items.stream().filter(item -> item.getQuantity() != 0) //
				.map(item -> {
					final CargoEntityItem entityItem = new CargoEntityItem();
					entityItem.setTypeId(item.getType().getId());
					entityItem.setQuantity(item.getQuantity());
					return entityItem;
				}).forEach(item -> entity.getItems().add(item));

		return entity;
	}

	@SuppressWarnings("unchecked")
	private CargoEntityId createEntityId(final Game game, final Object cargoHolder) {
		final Optional<CargoHolderDefinition<?>> optionalDefinition = cargoHolderDefinitions.stream()
				.filter(definition -> definition.matches(cargoHolder)).findAny();

		final CargoHolderDefinition<Object> cargoHolderDefinition = optionalDefinition
				.map(definition -> (CargoHolderDefinition<Object>) definition)
				.orElseThrow(() -> new IllegalArgumentException(
						CargoHolderDefinition.class.getName() + " must be implemented to match " + cargoHolder));

		final String cargoHolderType = cargoHolderDefinition.getCargoHolderType();
		final String cargoHolderId = cargoHolderDefinition.getCargoHolderId(cargoHolder);

		return new CargoEntityId(game.getId(), game.getYear(), cargoHolderType, cargoHolderId);
	}

	private void updateEntityIdForCurrentYear(final CargoEntity entity, final Game currentYear) {
		final CargoEntityId entityId = entity.getEntityId();
		entityId.setGameId(currentYear.getId());
		entityId.setYear(currentYear.getYear());
	}

	private CargoType getCargoTypeByEntity(final CargoEntityItem item) {
		if (cargoTypeByIdMap == null) {
			cargoTypeByIdMap = cargoTypes.stream().collect(Collectors.toMap(CargoType::getId, Function.identity()));
		}
		final CargoType cargoType = cargoTypeByIdMap.get(item.getTypeId());
		Preconditions.checkNotNull(cargoType, "No cargoType could be found for typeId " + item.getTypeId());

		return cargoType;
	}
}