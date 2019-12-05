package com.pim.stars.race.imp.effects;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;
import com.pim.stars.race.imp.persistence.RaceEntity;
import com.pim.stars.race.imp.persistence.RaceEntityCreator;

@Component
public class RaceGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private GameInitializationDataRaceCollection gameInitializationDataRaceCollection;
	@Autowired
	private RacePrimaryRacialTrait racePrimaryRacialTrait;
	@Autowired
	private RaceSecondaryRacialTraitCollection raceSecondaryRacialTraitCollection;
	@Autowired
	private RaceEntityCreator raceEntityCreator;
	@Autowired
	private IdCreator idCreator;

	@Override
	public int getSequence() {
		return 1000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {
		final List<RaceEntity> newEntities = gameInitializationDataRaceCollection.getValue(initializationData).stream()
				.map(race -> mapRaceToEntity(game, race)).collect(Collectors.toList());

		raceEntityCreator.createEntities(newEntities);
	}

	private RaceEntity mapRaceToEntity(final Game game, final RaceInitializationData race) {
		final String primaryRacialTraitId = racePrimaryRacialTrait.getValue(race).getId();
		final List<String> secondaryRacialTraitIds = raceSecondaryRacialTraitCollection.getValue(race).stream()
				.map(SecondaryRacialTrait::getId).sorted().collect(Collectors.toList());
		final String raceId = idCreator.createId();

		return raceEntityCreator.mapRaceToEntity(game.getId(), raceId, primaryRacialTraitId, secondaryRacialTraitIds);
	}

}
