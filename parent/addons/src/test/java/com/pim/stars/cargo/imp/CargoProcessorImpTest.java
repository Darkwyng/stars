package com.pim.stars.cargo.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.CargoTestConfiguration;
import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.policies.CargoHolderDefinition;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.game.api.Game;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CargoProcessorImpTest.TestConfiguration.class)
public class CargoProcessorImpTest {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private CargoType cargoType;

	@Mock
	private Game game;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(game.getId()).thenReturn("Id17");
		when(game.getYear()).thenReturn(28);
	}

	@Test
	public void testCargoHolderCanBeCreated() {
		final EntityForTest entity = new EntityForTest();

		final CargoHolder cargoHolder = cargoProcessor.createCargoHolder(game, entity);
		assertThat(cargoHolder, not(nullValue()));
		assertThat(cargoHolder.getQuantity(cargoType), is(0));
	}

	@Configuration
	@Import({ CargoTestConfiguration.WithPersistence.class })
	protected static class TestConfiguration {

		@Bean
		public CargoHolderDefinition<?> cargoHolderDefinition() {
			return new CargoHolderDefinition<EntityForTest>() {

				@Override
				public boolean matches(final Object object) {
					return object instanceof EntityForTest;
				}

				@Override
				public String getCargoHolderType() {
					return "T";
				}

				@Override
				public String getCargoHolderId(final EntityForTest object) {
					return String.valueOf(object.hashCode());
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

	private static final class EntityForTest {

	}
}
