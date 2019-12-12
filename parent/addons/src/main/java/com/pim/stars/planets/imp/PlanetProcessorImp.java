package com.pim.stars.planets.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProcessor;
import com.pim.stars.planets.imp.persistence.PlanetEntity;
import com.pim.stars.planets.imp.persistence.PlanetRepository;

@Component
public class PlanetProcessorImp implements PlanetProcessor {

	@Autowired
	private PlanetRepository planetRepository;

	@Override
	public Planet setPlanetOwnerId(final Game game, final Planet planet, final String raceId) {
		final PlanetEntity entity = planetRepository.findByGameIdAndYearAndName(game.getId(), game.getYear(),
				planet.getName());

		entity.setOwnerId(raceId);
		planetRepository.save(entity);

		return new PlanetImp(game, planet.getName(), raceId);
	}
}