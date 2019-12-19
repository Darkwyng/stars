package com.pim.stars.cargo.imp.persistence;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.pim.stars.cargo.api.CargoHolder.CargoItem;
import com.pim.stars.cargo.api.policies.CargoHolderDefinition;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.imp.AbstractCargoHolder.CargoItemImp;
import com.pim.stars.game.api.Game;

@Component
public class CargoPersistenceInterface {

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
	public Collection<CargoItem> loadItems(final Game game, final Object cargoHolder) {
		final String entityId = createEntityId(game, cargoHolder);
		final Optional<CargoEntity> optionalEntity = cargoRepository.findById(entityId);

		return optionalEntity //
				.map(entity -> entity.getItems().stream() //
						.map(this::initializeCargoItem).collect(toList())) //
				.orElse(Collections.emptyList());
	}

	/**
	 * Copy all persistent entities to the current year, so that historic data remains untouched by the game generation,
	 * that is starting, and so that loading items ({@link #loadItems()} finds the data also for the current year.
	 */
	public void cloneEntitiesOfPreviousYear(final Game previousYear, final Game currentYear) {

		final List<CargoEntity> modifiedEntities = cargoRepository
				.findByGameIdAndYear(previousYear.getId(), previousYear.getYear()).stream().peek(entity -> {
					final String entityId = updateEntityIdForCurrentYear(entity.getEntityId(), previousYear,
							currentYear);
					entity.setEntityId(entityId);

					entity.setGameId(currentYear.getId());
					entity.setYear(currentYear.getYear());
				}).collect(Collectors.toList());

		cargoRepository.saveAll(modifiedEntities); // will insert new entries, because the IDs have changed
	}

	private CargoItem initializeCargoItem(final CargoEntityItem item) {
		return new CargoItemImp(getCargoTypeByEntity(item), item.getQuantity());
	}

	private CargoEntity initializeEntity(final Game game, final Object cargoHolder, final Collection<CargoItem> items) {
		final String entityId = createEntityId(game, cargoHolder);
		final CargoEntity entity = new CargoEntity();
		entity.setEntityId(entityId);

		entity.setGameId(game.getId());
		entity.setYear(game.getYear());

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
	private String createEntityId(final Game game, final Object cargoHolder) {
		final Optional<CargoHolderDefinition<?>> optionalDefinition = cargoHolderDefinitions.stream()
				.filter(definition -> definition.matches(cargoHolder)).findAny();

		final CargoHolderDefinition<Object> cargoHolderDefinition = optionalDefinition
				.map(definition -> (CargoHolderDefinition<Object>) definition)
				.orElseThrow(() -> new IllegalArgumentException(
						CargoHolderDefinition.class.getName() + " must be implemented to match " + cargoHolder));

		final String cargoHolderType = cargoHolderDefinition.getCargoHolderType();
		final String cargoHolderId = cargoHolderDefinition.getCargoHolderId(cargoHolder);

		return getEntityIdForGame(game).append('#') //
				.append(cargoHolderType).append('#') //
				.append(cargoHolderId).toString();
	}

	private StringBuilder getEntityIdForGame(final Game game) {
		return new StringBuilder(game.getId()).append('#').append(game.getYear());
	}

	private String updateEntityIdForCurrentYear(final String entityId, final Game previousYear,
			final Game currentYear) {
		final int lengthOfPreviousYearId = getEntityIdForGame(previousYear).toString().length();
		final String entityIdSuffix = entityId.substring(lengthOfPreviousYearId);

		return getEntityIdForGame(currentYear).append(entityIdSuffix).toString();
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