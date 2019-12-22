package com.pim.stars.planets.imp;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties(PlanetProperties.class)
@ConfigurationProperties(prefix = "planets")
@PropertySource("classpath:com/pim/stars/planets/imp/planets.properties")
@Getter
@Setter
public class PlanetProperties {

	private List<String> names;
}
