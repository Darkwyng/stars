package com.pim.stars.mineral.testapi;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetEntity;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetRepository;
import com.pim.stars.planets.api.Planet;

public class MineralTestDataAccessor {

	@Autowired
	private MineralPlanetRepository mineralPlanetRepository;

	public int getPlanetMineCount(final Game game, final Planet planet) {
		return mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(), game.getYear(), planet.getName())
				.getMineCount();
	}

	public void setPlanetMineCount(final Game game, final Planet planet, final int mineCount) {
		final MineralPlanetEntity entity = mineralPlanetRepository.findByGameIdAndYearAndName(game.getId(),
				game.getYear(), planet.getName());
		entity.setMineCount(mineCount);
		mineralPlanetRepository.save(entity);
	}
}
