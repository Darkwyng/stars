package com.pim.stars.race.imp.persistence;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.imp.RaceImp;

@Component
public class RaceProviderImp implements RaceProvider {

	@Autowired
	private RaceRepository raceRepository;

	@Override
	public Race getRaceById(final Game game, final String raceId) {
		final RaceEntity entity = raceRepository.findByGameIdAndRaceId(game.getId(), raceId);
		Preconditions.checkNotNull(entity, "race must not be null for id " + raceId);

		return new RaceImp(entity.getEntityId().getRaceId());
	}

	@Override
	public Stream<Race> getRacesByGame(final Game game) {
		final Collection<RaceEntity> entities = raceRepository.findByGameId(game.getId());
		Preconditions.checkArgument(!entities.isEmpty(), "There must be races");

		return entities.stream().map(entity -> entity.getEntityId().getRaceId()).sorted()
				.map(raceId -> new RaceImp(raceId));
	}
}