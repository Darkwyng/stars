package com.pim.stars.production.imp.persistence;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.ProductionQueueEntry;
import com.pim.stars.production.imp.cost.ProductionCostImp.ProductionCostBuilderImp;

@Component
public class ProductionPersistenceInterface {

	@Autowired
	private ProductionRepository productionRepository;
	@Autowired
	private Collection<ProductionItemType> productionItemTypes;
	@Autowired
	private Collection<ProductionCostType> productionCostTypes;

	public void createNewQueue(final Game game, final Planet planet) {
		final ProductionEntity entity = initializeNewEntity(game, planet);

		productionRepository.save(entity);
	}

	private ProductionEntity initializeNewEntity(final Game game, final Planet planet) {
		final ProductionEntity entity = new ProductionEntity();
		entity.setEntityId(new PlanetEntityId(game.getId(), game.getYear(), planet.getName()));

		return entity;
	}

	public void addToQueue(final Game game, final Planet planet, final ProductionItemType itemType,
			final int numberOfItemsToBuild) {
		final ProductionEntityItem entityItem = new ProductionEntityItem();
		entityItem.setItemTypeId(itemType.getId());
		entityItem.setNumberOfItemsToBuild(numberOfItemsToBuild);

		final ProductionEntity entity = productionRepository
				.findById(new PlanetEntityId(game.getId(), game.getYear(), planet.getName()))
				.orElseGet(() -> initializeNewEntity(game, planet));

		entity.getItems().add(entityItem);
		productionRepository.save(entity);
	}

	/**
	 * Copy all persistent entities to the current year, so that historic data remains untouched by the game generation,
	 * that is starting, and so that loading items finds the data also for the current year.
	 */
	public void cloneEntitiesOfPreviousYear(final Game previousYear, final Game currentYear) {

		final List<ProductionEntity> modifiedEntities = productionRepository
				.findByGameIdAndYear(previousYear.getId(), previousYear.getYear()).peek(entity -> {
					updateEntityIdForCurrentYear(entity, currentYear);
				}).collect(Collectors.toList());

		productionRepository.saveAll(modifiedEntities); // will insert new entries, because the IDs have changed
	}

	private void updateEntityIdForCurrentYear(final ProductionEntity entity, final Game currentYear) {
		final PlanetEntityId entityId = entity.getEntityId();
		entityId.setGameId(currentYear.getId());
		entityId.setYear(currentYear.getYear());
	}

	public Stream<ProductionQueue> getQueues(final Game game) {
		return productionRepository.findByGameIdAndYear(game.getId(), game.getYear()).map(entity -> {
			final ProductionQueue queue = new ProductionQueue(entity.getEntityId().getPlanetName());
			entity.getItems().stream().forEach(entityItem -> {

				final ProductionItemType itemType = getItemTypeById(entityItem.getItemTypeId());
				final ProductionQueueEntry queueEntry = new ProductionQueueEntry(itemType);
				queueEntry.setNumberOfItemsToBuild(entityItem.getNumberOfItemsToBuild());

				mapInvestedCostsToQueueEntry(entityItem, queueEntry);

				queue.addEntry(queueEntry);
			});

			return queue;
		});
	}

	private void mapInvestedCostsToQueueEntry(final ProductionEntityItem entityItem, final ProductionQueueEntry entry) {
		final ProductionCostBuilder costBuilder = new ProductionCostBuilderImp();
		entityItem.getInvestedCosts().stream().forEach(entityInvestedCost -> {
			final ProductionCostType costType = getCostTypeById(entityInvestedCost.getCostTypeId());
			costBuilder.add(costType, entityInvestedCost.getAmount());
		});

		entry.addToInvestedCost(costBuilder.build());
	}

	public void persistModifiedQueues(final Game game, final List<ProductionQueue> queuesAfterProduction) {
		final List<ProductionEntity> modifiedEntities = queuesAfterProduction.stream()
				.map(queue -> mapQueueToEntity(game, queue)).collect(Collectors.toList());
		productionRepository.saveAll(modifiedEntities);
	}

	private ProductionEntity mapQueueToEntity(final Game game, final ProductionQueue queue) {
		final ProductionEntity entity = new ProductionEntity();
		entity.setEntityId(new PlanetEntityId(game.getId(), game.getYear(), queue.getPlanetName()));

		for (final ProductionQueueEntry entry : queue) {
			final ProductionEntityItem entityItem = new ProductionEntityItem();
			entityItem.setItemTypeId(entry.getType().getId());
			entityItem.setNumberOfItemsToBuild(entry.getNumberOfItemsToBuild());
			entry.getInvestedCost().getItems().stream().map(item -> {
				final ProductionEntityItemInvestedCost entityItemInvestedCost = new ProductionEntityItemInvestedCost();
				entityItemInvestedCost.setCostTypeId(item.getType().getId());
				entityItemInvestedCost.setAmount(item.getAmount());

				return entityItemInvestedCost;
			}).forEach(entityItemInvestedCost -> entityItem.getInvestedCosts().add(entityItemInvestedCost));
			entity.getItems().add(entityItem);
		}

		return entity;
	}

	private ProductionItemType getItemTypeById(final String id) {
		return productionItemTypes.stream().filter(type -> type.getId().equals(id)).findAny().get();
	}

	private ProductionCostType getCostTypeById(final String id) {
		return productionCostTypes.stream().filter(type -> type.getId().equals(id)).findAny().get();
	}
}