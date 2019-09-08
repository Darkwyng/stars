package com.pim.stars.mineral.imp.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations.MineralConcentration;
import com.pim.stars.mineral.api.extensions.PlanetMineralConcentrations.MineralConcentrations;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;

@Component
public class MineralGameInitializationPolicy implements GameInitializationPolicy {

	private static final Random RANDOM = new Random();

	@Autowired(required = false)
	private final List<MineralType> mineralTypes = new ArrayList<>();

	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private PlanetMineralConcentrations planetMineralConcentrations;

	@Override
	public int getSequence() {
		return 3000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {
		gamePlanetCollection.getValue(game).stream().forEach(planet -> {

			final MineralConcentrationsImp newConcentrations = new MineralConcentrationsImp();
			newConcentrations.initialize(mineralTypes);

			planetMineralConcentrations.setValue(planet, newConcentrations);
		});
	}

	private static class MineralConcentrationsImp implements MineralConcentrations {

		private final Collection<MineralConcentration> items = new ArrayList<>();

		@Override
		public Iterator<MineralConcentration> iterator() {
			return items.iterator();
		}

		public void initialize(final List<MineralType> mineralTypes) {
			mineralTypes.stream().forEach(type -> {
				final double amount = 1.2 * RANDOM.nextInt(100);
				items.add(new MineralConcentrationImp(type, amount));
			});
		}
	}

	private static class MineralConcentrationImp implements MineralConcentration {

		private final MineralType type;
		private final double amount;

		public MineralConcentrationImp(final MineralType type, final double amount) {
			this.type = type;
			this.amount = amount;
		}

		@Override
		public MineralType getType() {
			return type;
		}

		@Override
		public double getAmount() {
			return amount;
		}
	}
}