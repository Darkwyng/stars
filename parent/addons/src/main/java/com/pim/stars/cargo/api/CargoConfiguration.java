package com.pim.stars.cargo.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.imp.CargoProcessorImp;

public interface CargoConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public CargoProcessor cargoProcessor() {
			return new CargoProcessorImp();
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