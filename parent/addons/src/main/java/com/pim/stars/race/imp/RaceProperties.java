package com.pim.stars.race.imp;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(RaceProperties.class)
@ConfigurationProperties(prefix = "race")
public class RaceProperties {

	private List<String> primaryRacialTraitFilePaths = Arrays.asList("com/pim/stars/race/imp/primarytraits.xml");
	private List<String> secondaryRacialTraitFilePaths = Arrays.asList("com/pim/stars/race/imp/secondarytraits.xml");

	public List<String> getPrimaryRacialTraitFilePaths() {
		return primaryRacialTraitFilePaths;
	}

	public void setPrimaryRacialTraitFilePaths(final List<String> primaryRacialTraitFilePaths) {
		this.primaryRacialTraitFilePaths = primaryRacialTraitFilePaths;
	}

	public List<String> getSecondaryRacialTraitFilePaths() {
		return secondaryRacialTraitFilePaths;
	}

	public void setSecondaryRacialTraitFilePaths(final List<String> secondaryRacialTraitFilePaths) {
		this.secondaryRacialTraitFilePaths = secondaryRacialTraitFilePaths;
	}
}
