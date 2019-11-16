package com.pim.stars.mineral.imp;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@EnableConfigurationProperties(MineralProperties.class)
@ConfigurationProperties(prefix = "mineral")
@PropertySource("classpath:com/pim/stars/mineral/imp/mineral.properties")
@Validated // TODO: @Validated adds validation during startup of the application context, but a call to Validator.validate(...) would then fail, because MineralProperties is MineralProperties$$EnhancerBySpring and its fields will always be at the default value even after calling the setter explicitly.
public class MineralProperties {

	private List<String> typeIds;
	private int numberOfMinesToStartWith;

	@Positive
	private int baseConcentration;
	@Positive
	private int fractionalMiningPrecision;

	private int homeWorldMinimumConcentration;

	@Valid
	private RaceMiningSettings defaultSettings = new RaceMiningSettings();

	public List<String> getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(final List<String> typeIds) {
		this.typeIds = typeIds;
	}

	public RaceMiningSettings getDefaultSettings() {
		return defaultSettings;
	}

	public void setDefaultSettings(final RaceMiningSettings defaultSettings) {
		this.defaultSettings = defaultSettings;
	}

	public int getNumberOfMinesToStartWith() {
		return numberOfMinesToStartWith;
	}

	public void setNumberOfMinesToStartWith(final int numberOfMinesToStartWith) {
		this.numberOfMinesToStartWith = numberOfMinesToStartWith;
	}

	public int getBaseConcentration() {
		return baseConcentration;
	}

	public void setBaseConcentration(final int baseConcentration) {
		this.baseConcentration = baseConcentration;
	}

	public int getFractionalMiningPrecision() {
		return fractionalMiningPrecision;
	}

	public void setFractionalMiningPrecision(final int fractionMiningPrecision) {
		this.fractionalMiningPrecision = fractionMiningPrecision;
	}

	public int getHomeWorldMinimumConcentration() {
		return homeWorldMinimumConcentration;
	}

	public void setHomeWorldMinimumConcentration(final int homeWorldMinimumConcentration) {
		this.homeWorldMinimumConcentration = homeWorldMinimumConcentration;
	}

	@Validated
	public static class RaceMiningSettings {

		@Positive
		private int mineProductionCost;
		@Positive
		private double mineEfficiency;

		public RaceMiningSettings(final RaceMiningSettings defaultSettings) {
			this();
			this.mineProductionCost = defaultSettings.mineProductionCost;
			this.mineEfficiency = defaultSettings.mineEfficiency;
		}

		public RaceMiningSettings() {
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
