package com.pim.stars.turn.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.imp.TurnCreatorImp;
import com.pim.stars.turn.imp.policies.GameTurnEntityCreator;
import com.pim.stars.turn.imp.policies.utilities.GameEntityTransformerMapper;
import com.pim.stars.turn.imp.policies.utilities.TurnEntityCreatorMapper;

public interface TurnConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public TurnCreator turnCreator() {
			return new TurnCreatorImp();
		}

		@Bean
		public TurnEntityCreator<?> gameTurnEntityCreator() {
			return new GameTurnEntityCreator();
		}

		@Bean
		public GameEntityTransformerMapper gameEntityTransformerMapper() {
			return new GameEntityTransformerMapper();
		}

		@Bean
		public TurnEntityCreatorMapper turnEntityCreatorMapper() {
			return new TurnEntityCreatorMapper();
		}
	}

	@Configuration
	@Import({}) // Currently nothing is imported by this component
	public static class Complete extends Provided {

	}

	public static interface Required {
		// Currently nothing is required by this component
	}
}
