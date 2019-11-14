package com.pim.stars.game;

import static org.mockito.Mockito.mock;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.id.api.IdCreator;

@Configuration
@Import({ GameConfiguration.Provided.class })
@EnableAutoConfiguration // Required by @DataMongoTest
@DataMongoTest
public class GameTestConfiguration implements GameConfiguration.Required {

	@Bean
	@Override
	public DataExtender dataExtender() {
		return mock(DataExtender.class);
	}

	@Bean
	@Override
	public EffectProvider effectProvider() {
		return mock(EffectProvider.class);
	}

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}
}