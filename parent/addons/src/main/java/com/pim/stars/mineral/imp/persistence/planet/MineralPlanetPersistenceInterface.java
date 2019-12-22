package com.pim.stars.mineral.imp.persistence.planet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.planets.api.Planet;

@Component
public class MineralPlanetPersistenceInterface {

	@Autowired
	private List<MineralType> mineralTypes;
	@Autowired
	private MineralPlanetRepository mineralPlanetRepository;
	@Autowired
	private MineralProperties mineralProperties;

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

	public MineralPlanetForMining initializeHomeworld(final Game game, final Planet planet, final int mineCount) {
		final MineralPlanetEntity entity = mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(),
				game.getYear(), planet.getName());

		entity.setHomeworld(true);
		entity.setMineCount(mineCount);
		mineralPlanetRepository.save(entity);

		return new MineralPlanetForMining(entity);
	}

	public MineralPlanetForMining getMineralPlanetForMining(final Game game, final Planet planet) {
		final MineralPlanetEntity entity = mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(),
				game.getYear(), planet.getName());

		return new MineralPlanetForMining(entity);
	}

	public void persistMineralPlanetForMining(final MineralPlanetForMining mineralPlanetForMining) {
		if (mineralPlanetForMining.isModified()) {
			final MineralPlanetEntity entity = mineralPlanetForMining.modifyEntity();
			mineralPlanetRepository.save(entity);
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

	public class MineralPlanetForMining {

		private final MineralPlanetEntity entity;
		private final Map<MineralType, Double> modifiedFractionalMinedQuantities = new HashMap<>();

		public MineralPlanetForMining(final MineralPlanetEntity entity) {
			this.entity = entity;
		}

		public double getConcentration(final MineralType mineralType) {
			final Double quantity = entity.getMineralConcentrations().stream()
					.filter(item -> item.getMineralTypeId().equals(mineralType.getId()))
					.map(MineralTypeWithQuantity::getQuantity).findAny().get();

			if (entity.isHomeworld()) {
				return Math.max(quantity, mineralProperties.getHomeWorldMinimumConcentration());
			} else {
				return quantity;
			}
		}

		public double getFractionalMinedQuantity(final MineralType mineralType) {
			return entity.getFractionalMinedQuantities().stream()
					.filter(item -> item.getMineralTypeId().equals(mineralType.getId()))
					.map(MineralTypeWithQuantity::getQuantity).findAny().orElse(0.0);
		}

		public void setFractionalMinedQuantity(final MineralType mineralType, final double newQuantity) {
			modifiedFractionalMinedQuantities.put(mineralType, newQuantity);
		}

		protected boolean isModified() {
			return !modifiedFractionalMinedQuantities.isEmpty();
		}

		protected MineralPlanetEntity modifyEntity() {
			modifiedFractionalMinedQuantities.entrySet().stream().forEach(newItem -> {
				final MineralType mineralType = newItem.getKey();
				final double newValue = newItem.getValue();
				final Optional<MineralTypeWithQuantity> optionalItem = entity.getFractionalMinedQuantities().stream()
						.filter(persistedItem -> persistedItem.getMineralTypeId().equals(mineralType.getId()))
						.findAny();

				if (optionalItem.isPresent()) {
					optionalItem.get().setQuantity(newValue);
				} else {
					entity.getFractionalMinedQuantities()
							.add(new MineralTypeWithQuantity(mineralType.getId(), newValue));
				}
			});
			return entity;
		}
	}
}