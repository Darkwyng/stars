package com.pim.stars.cargo.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.CargoTestConfiguration;
import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.dataextension.api.Entity;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CargoProcessorImpTest.TestConfiguration.class)
public class CargoProcessorImpTest {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private CargoDataExtensionPolicy<EntityForTest> cargoDataExtensionPolicy;
	@Autowired
	private CargoType cargoType;

	@Test
	public void testCargoHolderCanBeCreated() {
		final EntityForTest entity = new EntityForTest();
		cargoDataExtensionPolicy.setValue(entity, cargoDataExtensionPolicy.getDefaultValue().get());

		final CargoHolder cargoHolder = cargoProcessor.createCargoHolder(entity);
		assertThat(cargoHolder, not(nullValue()));
		assertThat(cargoHolder.getQuantity(cargoType), is(0));
	}

	@Configuration
	protected static class TestConfiguration extends CargoTestConfiguration {

		@Bean
		public CargoDataExtensionPolicy<?> cargoDataExtensionPolicy() {
			return new CargoDataExtensionPolicy<EntityForTest>() {

				@Override
				public Class<EntityForTest> getEntityClass() {
					return EntityForTest.class;
				}
			};
		}

		@Bean
		public CargoType anyCargoType() {
			return new CargoType() {

				@Override
				public String getId() {
					return "anyCargoType";
				}
			};
		}
	}

	private static final class EntityForTest implements Entity<EntityForTest> {

		private final Map<String, Object> extensions = new HashMap<>();

		@Override
		public Class<EntityForTest> getEntityClass() {
			return EntityForTest.class;
		}

		@Override
		public Object get(final String key) {
			return extensions.get(key);
		}

		@Override
		public void set(final String key, final Object value) {
			extensions.put(key, value);
		}

	}
}
