package com.pim.stars.mineral.imp.effects;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.mineral.imp.MineralProperties.RaceMiningSettings;
import com.pim.stars.mineral.imp.persistence.race.MineralRaceEntity;
import com.pim.stars.mineral.imp.persistence.race.MineralRaceRepository;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.turn.api.Race;

@Component
public class MineralRaceGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private MineralProperties mineralProperties;
	@Autowired
	private RaceProvider raceProvider;
	@Autowired
	private MineralRaceRepository mineralRaceRepository;

	@Override
	public int getSequence() {
		return 3000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {

		final List<MineralRaceEntity> newEntities = raceProvider.getRacesByGame(game)
				.map(race -> mapRaceToEntity(game, race)).collect(Collectors.toList());

		mineralRaceRepository.saveAll(newEntities);
	}

	private MineralRaceEntity mapRaceToEntity(final Game game, final Race race) {
		final RaceMiningSettings miningSettings = mineralProperties.getDefaultSettings(); // TODO 4: this should come from the races in initializationData

		final MineralRaceEntity entity = new MineralRaceEntity();
		entity.getEntityId().setGameId(game.getId());
		entity.getEntityId().setRaceId(race.getId());
		entity.setMineEfficiency(miningSettings.getMineEfficiency());
		entity.setMineProductionCost(miningSettings.getMineProductionCost());

		return entity;
	}
}
