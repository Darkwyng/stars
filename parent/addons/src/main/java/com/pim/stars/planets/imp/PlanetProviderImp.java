package com.pim.stars.planets.imp;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.planets.imp.persistence.PlanetEntity;
import com.pim.stars.planets.imp.persistence.PlanetRepository;

@Component
public class PlanetProviderImp implements PlanetProvider {

	@Autowired
	private PlanetRepository planetRepository;

	@Override
	public Stream<Planet> getPlanetsByGame(final Game game) {
		return planetRepository.findByGameIdAndYear(game.getId(), game.getYear()).stream()
				.map(entity -> mapEntityToPlanet(entity, game));
	}

	private Planet mapEntityToPlanet(final PlanetEntity entity, final Game game) {
		return new PlanetImp(game, entity.getEntityId().getName(), entity.getOwnerId());
	}

	@Override
	public Planet getPlanet(final Game game, final String planetName) {
		final PlanetEntity entity = planetRepository.findByGameIdAndYearAndName(game.getId(), game.getYear(),
				planetName);

		return mapEntityToPlanet(entity, game);
	}
}