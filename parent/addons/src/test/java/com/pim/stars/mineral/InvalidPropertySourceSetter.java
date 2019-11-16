package com.pim.stars.mineral;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

@Component
public class InvalidPropertySourceSetter {

	@EventListener
	public void onApplicationEvent(final ApplicationEnvironmentPreparedEvent event) {
		final Properties properties = new Properties();
		properties.setProperty("mineral.fractionalMiningPrecision", "0"); // so that MineralProperties will not be validated successfully
		properties.setProperty("this.example", "is.this"); // so that we can check whether this class was called

		final PropertiesPropertySource propertySource = new PropertiesPropertySource("InvalidPropertySource",
				properties);

		event.getEnvironment().getPropertySources().addFirst(propertySource);
	}

}