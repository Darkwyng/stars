package com.pim.stars.mineral.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.api.policies.CargoType.CargoTypeFactory;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;

@Configuration
public class MineralTypeCreator {

	@Autowired
	private ApplicationContext applicationContext;

	/** This class is autowired lazily to resolve a circular dependency. */
	private CargoProcessor cargoProcessor;

	/** Create the mineral types that are configured. */
	@Bean
	public List<MineralType> mineralTypes(final MineralProperties mineralProperties) {
		return mineralProperties.getTypeIds().stream().map(GenericMineralType::new).collect(Collectors.toList());
	}

	/** Register the mineral types also as cargo type. */
	@Bean
	public CargoTypeFactory mineralCargoTypeFactory(final List<MineralType> mineralTypes) {
		return () -> mineralTypes.stream().map(mineralType -> (CargoType) mineralType).collect(Collectors.toList());
	}

	/** Register the mineral types also as production cost type. */
	@Bean
	public ProductionCostTypeFactory mineralProductionCostTypeFactory(final List<MineralType> mineralTypes) {
		return () -> mineralTypes.stream().map(mineralType -> (ProductionCostType) mineralType)
				.collect(Collectors.toList());
	}

	private CargoProcessor getCargoProcessor() {
		if (cargoProcessor == null) {
			cargoProcessor = applicationContext.getBean(CargoProcessor.class);
		}

		return cargoProcessor;
	}

	private class GenericMineralType implements MineralType {

		private final String id;

		public GenericMineralType(final String id) {
			this.id = id;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public void deduct(final Game game, final Planet planet, final int amount) {
			getCargoProcessor().createCargoHolder(game, planet).transferToNowhere().quantity(this, amount).execute();
		}

		@Override
		public String toString() {
			return "GenericMineralType [id=" + id + ", hashCode=" + hashCode() + "]";
		}
	}

}