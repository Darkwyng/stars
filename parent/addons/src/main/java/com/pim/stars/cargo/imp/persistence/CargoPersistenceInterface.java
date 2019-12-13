package com.pim.stars.cargo.imp.persistence;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.Cargo.CargoItem;
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

	/** Create a map of type by ID after the collection has been autowired. */
	@PostConstruct
	public void initialize() {
		cargoTypeByIdMap = cargoTypes.stream().collect(Collectors.toMap(CargoType::getId, Function.identity()));
	}

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

	private CargoItem initializeCargoItem(final CargoEntityItem item) {
		final CargoType cargoType = cargoTypeByIdMap.get(item.getTypeId());
		return new CargoItemImp(cargoType, item.getQuantity());
	}

	private CargoEntity initializeEntity(final Game game, final Object cargoHolder, final Collection<CargoItem> items) {
		final String entityId = createEntityId(game, cargoHolder);
		final CargoEntity entity = new CargoEntity();
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
	private String createEntityId(final Game game, final Object cargoHolder) {
		final Optional<CargoHolderDefinition<?>> optionalDefinition = cargoHolderDefinitions.stream()
				.filter(definition -> definition.matches(cargoHolder)).findAny();

		final CargoHolderDefinition<Object> cargoHolderDefinition = optionalDefinition
				.map(definition -> (CargoHolderDefinition<Object>) definition)
				.orElseThrow(() -> new IllegalArgumentException(
						CargoHolderDefinition.class.getName() + " must be implemented to match " + cargoHolder));

		final String cargoHolderType = cargoHolderDefinition.getCargoHolderType();
		final String cargoHolderId = cargoHolderDefinition.getCargoHolderId(cargoHolder);

		return new StringBuilder(game.getId()).append('#') //
				.append(game.getYear()).append('#') //
				.append(cargoHolderType).append('#') //
				.append(cargoHolderId).toString();
	}
}