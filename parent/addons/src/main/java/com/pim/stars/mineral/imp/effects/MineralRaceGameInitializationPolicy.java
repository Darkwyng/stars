package com.pim.stars.mineral.imp.effects;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.mineral.imp.MineralProperties.RaceMiningSettings;
import com.pim.stars.mineral.imp.persistence.MineralRaceEntity;
import com.pim.stars.mineral.imp.persistence.MineralRaceRepository;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.turn.api.Race;

@Component
public class MineralRaceGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private MineralProperties mineralProperties;
	@Autowired
	private GameRaceCollection gameRaceCollection;
	@Autowired
	private MineralRaceRepository mineralRaceRepository;

	@Override
	public int getSequence() {
		return 3000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {

		final List<MineralRaceEntity> newEntities = gameRaceCollection.getValue(game).stream()
				.map(this::mapRaceToEntity).collect(Collectors.toList());
		Preconditions.checkArgument(!newEntities.isEmpty(), "There must be races");

		mineralRaceRepository.saveAll(newEntities);

	}

	private MineralRaceEntity mapRaceToEntity(final Race race) {
		final RaceMiningSettings miningSettings = mineralProperties.getDefaultSettings(); // TODO: this should come from the races in initializationData

		final MineralRaceEntity entity = new MineralRaceEntity();
		entity.setRaceId(race.getId());
		entity.setMineEfficiency(miningSettings.getMineEfficiency());
		entity.setMineProductionCost(miningSettings.getMineProductionCost());

		return entity;
	}
}
