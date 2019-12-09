package com.pim.stars.planets.imp;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.planets.PlanetTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PlanetTestConfiguration.WithoutPersistence.class)
public class PlanetPropertiesTest {

	@Autowired
	private PlanetProperties planetProperties;

	@Autowired
	private Environment environment;

	@Value("${planets.names}")
	private List<String> names;

	@Test
	public void testThatPropertiesAreLoaded() {
		final List<?> namesFromEnvironment = environment.getProperty("planets.names", List.class);
		assertAll(() -> assertThat("From @Value", names, allOf(not(nullValue()), not(empty()))),
				() -> assertThat("From environment", namesFromEnvironment, allOf(not(nullValue()), not(empty()))),
				() -> assertThat("From class", planetProperties.getNames(), allOf(not(nullValue()), not(empty()))));
	}
}
