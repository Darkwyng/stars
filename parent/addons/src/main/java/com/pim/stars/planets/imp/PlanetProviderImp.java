package com.pim.stars.planets.imp;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.planets.imp.persistence.PlanetRepository;

@Component
public class PlanetProviderImp implements PlanetProvider {

	@Autowired
	private PlanetRepository planetRepository;

	@Override
	public Stream<Planet> getPlanetsByGame(final Game game) {
		return planetRepository.findByGameIdAndYear(game.getId(), game.getYear()).stream()
				.map(entity -> new PlanetImp(game, entity.getName(), entity.getOwnerId()));
	}
}