package com.pim.stars.dataextension.imp;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.dataextension.DataExtensionTestConfiguration;
import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataExtenderImpTest.TestConfiguration.class)
public class DataExtenderImpTest {

	@Autowired
	private DataExtender dataExtender;

	@Test
	public void testThatDataExtensionPoliciesAreUsed() {
		final EntityForTest entity = new EntityForTest();
		final SimpleDataTypeDataExtensionPolicyForTest simplePolicy = new SimpleDataTypeDataExtensionPolicyForTest();
		final EmptyDataExtensionPolicyForTest emptyPolicy = new EmptyDataExtensionPolicyForTest();
		final ListForTestDataExtensionPolicy listPolicy = new ListForTestDataExtensionPolicy();

		assertAll(() -> assertThat(entity.extensions.keySet(), empty()),
				() -> assertThat(simplePolicy.getValue(entity), nullValue()),
				() -> assertThat(emptyPolicy.getValue(entity), nullValue()),
				() -> assertThat(listPolicy.getValue(entity), nullValue()));

		dataExtender.extendData(entity, EntityForTest.class);

		assertAll(
				() -> assertThat(entity.extensions.keySet(),
						containsInAnyOrder("SimpleDataTypeDataExtensionPolicyForTest", "ListForTest")),
				() -> assertThat(simplePolicy.getValue(entity), is("Hello world")),
				() -> assertThat(emptyPolicy.getValue(entity), nullValue()),
				() -> assertThat(listPolicy.getValue(entity), hasItems(1, 2, 3)));

		simplePolicy.setValue(entity, "Hello, again!");
		assertThat(simplePolicy.getValue(entity), is("Hello, again!"));
	}

	private static final class EntityForTest implements Entity {

		private final Map<String, Object> extensions = new HashMap<>();

		@Override
		public Object get(final String key) {
			return extensions.get(key);
		}

		@Override
		public void set(final String key, final Object value) {
			extensions.put(key, value);
		}
	}

	private static final class SimpleDataTypeDataExtensionPolicyForTest implements DataExtensionPolicy<String> {

		@Override
		public Class<? extends Entity> getEntityClass() {
			return EntityForTest.class;
		}

		@Override
		public Optional<? extends String> getDefaultValue() {
			return Optional.of("Hello world");
		}

	}

	private static final class EmptyDataExtensionPolicyForTest implements DataExtensionPolicy<Long> {

		@Override
		public Class<? extends Entity> getEntityClass() {
			return EntityForTest.class;
		}

		@Override
		public Optional<? extends Long> getDefaultValue() {
			return Optional.empty();
		}
	}

	private static final class ListForTestDataExtensionPolicy implements DataExtensionPolicy<List<Integer>> {

		@Override
		public Class<? extends Entity> getEntityClass() {
			return EntityForTest.class;
		}

		@Override
		public Optional<? extends List<Integer>> getDefaultValue() {
			return Optional.of(Arrays.asList(1, 2, 3));
		}
	}

	@Configuration
	protected static class TestConfiguration extends DataExtensionTestConfiguration {

		@Bean
		public DataExtensionPolicy<?> simpleDataType() {
			return new SimpleDataTypeDataExtensionPolicyForTest();
		}

		@Bean
		public DataExtensionPolicy<?> empty() {
			return new EmptyDataExtensionPolicyForTest();
		}

		@Bean
		public DataExtensionPolicy<?> list() {
			return new ListForTestDataExtensionPolicy();
		}

		@Bean
		public DataExtensionPolicy<?> notUsedPolcy() {
			return new DataExtensionPolicy<Object>() {

				@Override
				public Class<? extends Entity> getEntityClass() {
					return Entity.class;
				}

				@Override
				public Optional<?> getDefaultValue() {
					throw new AssertionError(
							"This should never be called, because this implementation has a different qualifier.");
				}
			};
		}
	}
}