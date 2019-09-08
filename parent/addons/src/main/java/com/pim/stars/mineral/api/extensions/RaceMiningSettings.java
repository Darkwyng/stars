package com.pim.stars.mineral.api.extensions;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.mineral.api.extensions.RaceMiningSettings.MiningSettings;
import com.pim.stars.mineral.imp.MineralProperties;
import com.pim.stars.turn.api.Race;

@Component
public class RaceMiningSettings implements DataExtensionPolicy<Race, MiningSettings> {

	@Autowired
	private MineralProperties mineralProperties;

	@Override
	public Class<Race> getEntityClass() {
		return Race.class;
	}

	@Override
	public Optional<? extends MiningSettings> getDefaultValue() {
		return Optional.of(new MiningSettings(mineralProperties.getDefaultSettings()));
	}

	public static class MiningSettings {

		private int mineProductionCost;
		private double mineEfficiency;

		public MiningSettings(final MiningSettings defaultSettings) {
			this();
			this.mineProductionCost = defaultSettings.mineProductionCost;
			this.mineEfficiency = defaultSettings.mineEfficiency;
		}

		public MiningSettings() {
			super();
		}

		public int getMineProductionCost() {
			return mineProductionCost;
		}

		public void setMineProductionCost(final int mineProductionCost) {
			this.mineProductionCost = mineProductionCost;
		}

		public double getMineEfficiency() {
			return mineEfficiency;
		}

		public void setMineEfficiency(final double mineEfficiency) {
			this.mineEfficiency = mineEfficiency;
		}
	}
}
