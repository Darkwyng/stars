package com.pim.stars.dataextension.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.imp.DataExtenderImp;

public interface DataExtensionConfiguration {

	@Configuration
	public static class Provided {

		@Bean
		public DataExtender dataExtender() {
			return new DataExtenderImp();
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
