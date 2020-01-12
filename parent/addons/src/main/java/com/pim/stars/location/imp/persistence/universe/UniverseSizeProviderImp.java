package com.pim.stars.location.imp.persistence.universe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.UniverseSizeProvider;

@Component
public class UniverseSizeProviderImp implements UniverseSizeProvider {

	@Autowired
	private UniverseRepository universeRepository;

	@Override
	public UniverseSize getUniverseSize(final Game game) {
		return universeRepository.getUniverseSize(game);
	}
}