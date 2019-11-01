package com.pim.stars.mineral.imp;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.pim.stars.mineral.api.extensions.RaceMiningSettings.MiningSettings;

@Component
@EnableConfigurationProperties(MineralProperties.class)
@ConfigurationProperties(prefix = "mineral")
@PropertySource("classpath:com/pim/stars/mineral/imp/mineral.properties")
@Validated
public class MineralProperties {

	private List<String> typeIds;
	private int numberOfMinesToStartWith;
	@NonNull
	private int baseConcentration;
	@NonNull
	private int fractionalMiningPrecision;
	private int homeWorldMinimumConcentration;

	private MiningSettings defaultSettings = new MiningSettings();

	public List<String> getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(final List<String> typeIds) {
		this.typeIds = typeIds;
	}

	public MiningSettings getDefaultSettings() {
		return defaultSettings;
	}

	public void setDefaultSettings(final MiningSettings defaultSettings) {
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
}
