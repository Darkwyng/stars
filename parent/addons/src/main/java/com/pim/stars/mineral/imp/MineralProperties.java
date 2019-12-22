package com.pim.stars.mineral.imp;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@EnableConfigurationProperties(MineralProperties.class)
@ConfigurationProperties(prefix = "mineral")
@PropertySource("classpath:com/pim/stars/mineral/imp/mineral.properties")
@Validated
@Getter
@Setter
public class MineralProperties {

	private List<String> typeIds;
	private int numberOfMinesToStartWith;
	@NonNull
	private int baseConcentration;
	@NonNull
	private int fractionalMiningPrecision;
	private int homeWorldMinimumConcentration;

	private RaceMiningSettings defaultSettings = new RaceMiningSettings();

	@Getter
	@Setter
	@NoArgsConstructor
	public static class RaceMiningSettings {

		@NonNull
		private int mineProductionCost;
		@NonNull
		private double mineEfficiency;

		public RaceMiningSettings(final RaceMiningSettings defaultSettings) {
			this();
			this.mineProductionCost = defaultSettings.mineProductionCost;
			this.mineEfficiency = defaultSettings.mineEfficiency;
		}
	}
}
