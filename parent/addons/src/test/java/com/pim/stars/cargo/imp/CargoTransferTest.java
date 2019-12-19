package com.pim.stars.cargo.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

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
import com.pim.stars.cargo.api.CargoHolder.CargoItem;
import com.pim.stars.cargo.api.CargoHolder.CargoTransferResult;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.policies.CargoHolderDefinition;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.api.policies.CargoType.CargoTypeFactory;
import com.pim.stars.cargo.imp.AbstractCargoHolder.CargoItemImp;
import com.pim.stars.game.api.Game;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CargoTransferTest.TestConfiguration.class)
public class CargoTransferTest {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private Collection<CargoType> cargoTypes;

	@Mock
	private Game game;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(game.getId()).thenReturn("Id17");
		when(game.getYear()).thenReturn(28);
	}

	@Test
	public void testCargoCanBeTransferred() {
		final CargoType cargoType = cargoTypes.iterator().next();

		final EntityForTest firstEntity = new EntityForTest();

		final CargoHolder first = cargoProcessor.createCargoHolder(game, firstEntity);
		assertThat(first, not(nullValue()));
		assertThat(first.getQuantity(cargoType), is(0));

		// From nowhere:
		{
			final CargoTransferResult result = first.transferFromNowhere().quantity(cargoType, 17).execute();
			assertAll(() -> assertThat(first.getQuantity(cargoType), is(17)),
					() -> assertThat(result.getTarget(), is(first)), //
					() -> assertThat(result.getTransferredItems(), hasSize(1)),
					() -> assertThat(result.getTransferredItems().iterator().next().getQuantity(), is(17)),
					() -> assertThat(result.getTransferredItems().iterator().next().getType(), is(cargoType)));
		}
		final EntityForTest secondEntity = new EntityForTest();
		final CargoHolder second = cargoProcessor.createCargoHolder(game, secondEntity);

		// TransferTo: from first to second:
		{
			final CargoTransferResult result = first.transferTo(second).quantity(cargoType, 13).execute();
			assertAll(() -> assertThat(first.getQuantity(cargoType), is(4)),
					() -> assertThat(second.getQuantity(cargoType), is(13)),
					() -> assertThat(result.getSource(), is(first)), //
					() -> assertThat(result.getTarget(), is(second)), //
					() -> assertThat(result.getTransferredItems(), hasSize(1)),
					() -> assertThat(result.getTransferredItems().iterator().next().getQuantity(), is(13)),
					() -> assertThat(result.getTransferredItems().iterator().next().getType(), is(cargoType)));
		}
		// To nowhere:
		{
			final CargoTransferResult result = second.transferToNowhere().quantity(cargoType, 1).quantity(cargoType, 2)
					.execute();
			assertAll(() -> assertThat(second.getQuantity(cargoType), is(11)),
					() -> assertThat(result.getSource(), is(second)), //
					() -> assertThat(result.getTransferredItems(), hasSize(1)),
					() -> assertThat(result.getTransferredItems().iterator().next().getQuantity(), is(2)),
					() -> assertThat(result.getTransferredItems().iterator().next().getType(), is(cargoType)));
		}

		// transferFrom: from first to second:
		{
			final CargoItem item = new CargoItemImp(cargoType, 3);
			final CargoTransferResult result = second.transferFrom(first).item(item).item(item).quantity(cargoType, 4)
					.quantity(cargoType, 2).execute();
			assertAll(() -> assertThat(first.getQuantity(cargoType), is(2)),
					() -> assertThat(second.getQuantity(cargoType), is(13)),
					() -> assertThat(result.getSource(), is(first)), //
					() -> assertThat(result.getTarget(), is(second)), //
					() -> assertThat(result.getTransferredItems(), hasSize(1)),
					() -> assertThat(result.getTransferredItems().iterator().next().getQuantity(), is(2)),
					() -> assertThat(result.getTransferredItems().iterator().next().getType(), is(cargoType)));
		}

		// transfer zero:
		{
			final CargoTransferResult result = second.transferFrom(first).quantity(cargoType, 0).execute();
			assertAll(() -> assertThat(first.getQuantity(cargoType), is(2)),
					() -> assertThat(second.getQuantity(cargoType), is(13)),
					() -> assertThat(result.getSource(), is(first)), //
					() -> assertThat(result.getTarget(), is(second)), //
					() -> assertThat(result.getTransferredItems(), empty()));
		}
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
		public CargoTypeFactory anyCargoTypeForTransferTest() {
			return () -> Arrays.asList(() -> "anyCargoTypeForTransferTest");
		}
	}

	private static final class EntityForTest {

	}
}