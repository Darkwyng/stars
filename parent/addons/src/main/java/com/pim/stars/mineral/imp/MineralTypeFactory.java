package com.pim.stars.mineral.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.planets.api.Planet;

@Configuration
public class MineralTypeFactory {

	@Autowired
	private CargoProcessor cargoProcessor;

	@Bean
	public List<MineralType> mineralTypes(final MineralProperties mineralProperties) {
		return mineralProperties.getTypeIds().stream().map(GenericMineralType::new).collect(Collectors.toList());
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
			cargoProcessor.createCargoHolder(planet).transferToNowhere().quantity(this, amount).execute();
		}
	}
}