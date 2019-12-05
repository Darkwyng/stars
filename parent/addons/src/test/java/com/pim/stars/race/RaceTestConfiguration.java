package com.pim.stars.race;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.imp.persistence.RaceRepository;

public class RaceTestConfiguration implements RaceConfiguration.Required {

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Bean
	@Override
	public DataExtender dataExtender() {
		return mock(DataExtender.class);
	}

	@Configuration
	@Import({ RaceTestConfiguration.class, RaceConfiguration.Provided.class })
	public class WithoutPersistence {

		@MockBean
		private RaceRepository raceRepository;
	}
}