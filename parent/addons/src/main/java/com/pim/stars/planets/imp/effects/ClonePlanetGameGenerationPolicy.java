package com.pim.stars.planets.imp.effects;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.planets.imp.persistence.PlanetEntity;
import com.pim.stars.planets.imp.persistence.PlanetRepository;

@Component
public class ClonePlanetGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private PlanetRepository planetRepository;

	@Override
	public int getSequence() {
		return 10;
	}

	@Override
	public void generateGame(final GameGenerationContext context) {
		final Game previousYear = context.getPreviousYear();
		final Game currentYear = context.getCurrentYear();

		final List<PlanetEntity> newEntities = planetRepository
				.findByGameIdAndYear(previousYear.getId(), previousYear.getYear()).stream().map(previousEntity -> {
					final PlanetEntity newEntity = new PlanetEntity();
					newEntity.getEntityId().setGameId(currentYear.getId());
					newEntity.getEntityId().setYear(currentYear.getYear());
					newEntity.getEntityId().setName(previousEntity.getEntityId().getName());
					newEntity.setOwnerId(previousEntity.getOwnerId());

					return newEntity;
				}).collect(Collectors.toList());

		planetRepository.saveAll(newEntities);
	}
}