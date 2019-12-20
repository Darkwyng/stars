package com.pim.stars.mineral.imp.persistence.planet;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetEntity.MineralTypeWithQuantity;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetEntity.PlanetEntityId;
import com.pim.stars.planets.api.Planet;

@Component
public class MineralPlanetPersistenceInterface {

	@Autowired
	private List<MineralType> mineralTypes;
	@Autowired
	private MineralPlanetRepository mineralPlanetRepository;
	@Autowired
	private MineralProperties mineralProperties;

	private Map<String, MineralType> mineralTypeByIdMap = null;

	public void createPlanetsWithConcentrations(final Game game, final Stream<Planet> planetStream,
			final Function<MineralType, Double> concentrationSupplier) {
		final List<MineralPlanetEntity> newEntities = planetStream.map(planet -> {
			final MineralPlanetEntity entity = new MineralPlanetEntity();
			entity.setEntityId(new PlanetEntityId(game.getId(), game.getYear(), planet.getName()));

			mineralTypes.stream()
					.map(type -> new MineralTypeWithQuantity(type.getId(), concentrationSupplier.apply(type)))
					.forEach(concentration -> entity.getMineralConcentrations().add(concentration));

			return entity;
		}).collect(Collectors.toList());

		mineralPlanetRepository.saveAll(newEntities);
	}

	public Map<MineralType, Double> initializeHomeworld(final Game game, final Planet planet, final int mineCount) {
		final MineralPlanetEntity entity = mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(),
				game.getYear(), planet.getName());

		entity.setHomeworld(true);
		entity.setMineCount(mineCount);
		mineralPlanetRepository.save(entity);

		return getConcentrationsByType(entity);
	}

	private Map<MineralType, Double> getConcentrationsByType(final MineralPlanetEntity entity) {
		if (mineralTypeByIdMap == null) {
			mineralTypeByIdMap = mineralTypes.stream()
					.collect(Collectors.toMap(MineralType::getId, Function.identity()));
		}

		return entity.getMineralConcentrations().stream()
				.collect(Collectors.toMap(
						typeWithQuantity -> mineralTypeByIdMap.get(typeWithQuantity.getMineralTypeId()),
						typeWithQuantity -> getConcentrationQuantity(entity, typeWithQuantity)));
	}

	public Map<MineralType, Double> getConcentrationsByType(final Game game, final Planet planet) {
		final MineralPlanetEntity entity = mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(),
				game.getYear(), planet.getName());

		return getConcentrationsByType(entity);
	}

	private Double getConcentrationQuantity(final MineralPlanetEntity planet,
			final MineralTypeWithQuantity typeWithQuantity) {
		final Double quantity = typeWithQuantity.getQuantity();
		if (planet.isHomeworld()) {
			return Math.max(quantity, mineralProperties.getHomeWorldMinimumConcentration());
		} else {
			return quantity;
		}
	}

	public void buildMines(final Game game, final Planet planet, final int numberOfNewMines) {
		final MineralPlanetEntity entity = mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(),
				game.getYear(), planet.getName());

		final int previousMines = entity.getMineCount();
		entity.setMineCount(previousMines + numberOfNewMines);

		mineralPlanetRepository.save(entity);
	}

	public int getMineCount(final Game game, final Planet planet) {
		final MineralPlanetEntity entity = mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(),
				game.getYear(), planet.getName());

		return entity.getMineCount();
	}

	/**
	 * Copy all persistent entities to the current year, so that historic data remains untouched by the game generation,
	 * that is starting, and so that loading items finds the data also for the current year.
	 */
	public void cloneEntitiesOfPreviousYear(final Game previousYear, final Game currentYear) {
		final List<MineralPlanetEntity> modifiedEntities = mineralPlanetRepository
				.findByGameIdAndYear(previousYear.getId(), previousYear.getYear()).stream().peek((entity) -> {
					final PlanetEntityId newEntityId = new PlanetEntityId(currentYear.getId(), currentYear.getYear(),
							entity.getEntityId().getName());
					entity.setEntityId(newEntityId);

				}).collect(Collectors.toList());

		mineralPlanetRepository.saveAll(modifiedEntities); // will insert new entries, because the IDs have changed
	}
}