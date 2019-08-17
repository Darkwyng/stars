package com.pim.stars.race.testapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.race.api.RaceConfiguration;

@Configuration
@Import({ RaceConfiguration.Complete.class })
public class RaceTestApiConfiguration {

	@Bean
	public RaceTestDataProvider raceTestDataProvider() {
		return new RaceTestDataProvider();
	}
}
