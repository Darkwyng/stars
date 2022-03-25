package com.pim.stars.colonization.imp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties(ColonizationProperties.class)
@ConfigurationProperties(prefix = "colonization")
@Getter
@Setter
public class ColonizationProperties {

	private int defaultPlanetCapacity = 10000;
	private int defaultInitialPopulation = 250;
	private int colonistsPerCargoUnit = 100;
}
