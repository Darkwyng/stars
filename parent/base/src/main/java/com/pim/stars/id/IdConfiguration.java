package com.pim.stars.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.id.api.IdCreator;
import com.pim.stars.id.imp.IdCreatorImp;

public interface IdConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public IdCreator idCreator() {
			return new IdCreatorImp();
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
