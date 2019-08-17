package com.pim.stars.planets;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.planets.api.PlanetConfiguration;
import com.pim.stars.turn.api.TurnCreator;

@Configuration
@Import({ PlanetConfiguration.Provided.class })
public class PlanetTestConfiguration implements PlanetConfiguration.Required {

	@Bean
	@Override
	public DataExtender dataExtender() {
		return mock(DataExtender.class);
	}

	@Bean
	@Override
	public TurnCreator turnCreator() {
		return mock(TurnCreator.class);
	}
}