package com.pim.stars.race.imp;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties(RaceProperties.class)
@ConfigurationProperties(prefix = "race")
@Getter
@Setter
public class RaceProperties {

	private List<String> primaryRacialTraitFilePaths = Arrays.asList("com/pim/stars/race/imp/primarytraits.xml");
	private List<String> secondaryRacialTraitFilePaths = Arrays.asList("com/pim/stars/race/imp/secondarytraits.xml");
}
