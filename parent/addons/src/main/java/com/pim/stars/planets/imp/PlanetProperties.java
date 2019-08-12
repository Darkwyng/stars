package com.pim.stars.planets.imp;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties(PlanetProperties.class)
@ConfigurationProperties(prefix = "planets")
@PropertySource("classpath:com/pim/stars/planets/imp/planets.properties")
public class PlanetProperties {

	private List<String> names;

	public List<String> getNames() {
		return names;
	}

	public void setNames(final List<String> names) {
		this.names = names;
	}
}
