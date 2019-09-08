package com.pim.stars.cargo.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import com.pim.stars.cargo.api.Cargo.CargoItem;
import com.pim.stars.cargo.api.CargoHolder;
import com.pim.stars.cargo.api.CargoHolder.CargoTransferResult;
import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.imp.AbstractCargoHolder.CargoItemImp;
import com.pim.stars.dataextension.api.Entity;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CargoTransferTest.TestConfiguration.class)
public class CargoTransferTest {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private CargoDataExtensionPolicy<EntityForTest> cargoDataExtensionPolicy;
	@Autowired
	private CargoType cargoType;

	@Test
	public void testCargoCanBeTransferred() {
		final EntityForTest firstEntity = new EntityForTest();
		cargoDataExtensionPolicy.setValue(firstEntity, cargoDataExtensionPolicy.getDefaultValue().get());

		final CargoHolder first = cargoProcessor.createCargoHolder(firstEntity);
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
		cargoDataExtensionPolicy.setValue(secondEntity, cargoDataExtensionPolicy.getDefaultValue().get());
		final CargoHolder second = cargoProcessor.createCargoHolder(secondEntity);

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