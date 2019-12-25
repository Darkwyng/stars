package com.pim.stars.game;

import static org.mockito.Mockito.mock;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.imp.persistence.GameRepository;
import com.pim.stars.id.api.IdCreator;

public class GameTestConfiguration implements GameConfiguration.Required {

	@Bean
	@Override
	public DataExtender dataExtender() {
		return mock(DataExtender.class);
	}

	@Bean
	@Override
	public EffectExecutor effectExecutor() {
		return mock(EffectExecutor.class);
	}

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Configuration
	@Import({ GameTestConfiguration.class, GameConfiguration.Provided.class })
	@Profile("WithoutPersistence")
	public class WithoutPersistence {

		@MockBean
		private GameRepository repository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ GameTestConfiguration.class, GameConfiguration.Provided.class })
	@Profile("WithPersistence")
	public class WithPersistence {

	}
}