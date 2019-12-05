package com.pim.stars.race.imp.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RaceEntityCreator {

	@Autowired
	private RaceRepository raceRepository;

	public RaceEntity mapRaceToEntity(final String gameId, final String raceId, final String primaryRacialTraitId,
			final List<String> secondaryRacialTraitIds) {
		final RaceEntity entity = new RaceEntity();
		entity.setGameId(gameId);
		entity.setRaceId(raceId);
		entity.setPrimaryRacialTraitId(primaryRacialTraitId);
		entity.setSecondaryRacialTraitIds(secondaryRacialTraitIds);
		return entity;
	}

	public void createEntities(final Collection<RaceEntity> newEntities) {
		raceRepository.saveAll(newEntities);
	}
}
