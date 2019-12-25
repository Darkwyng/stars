package com.pim.stars.production;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.production.ProductionConfigurationTest.TestConfiguration;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.api.policies.ProductionItemType.ProductionItemTypeFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("WithoutPersistence")
public class ProductionConfigurationTest {

	@Autowired
	private Collection<ProductionCostType> productionCostTypes;
	@Autowired
	private Collection<ProductionItemType> productionItemTypes;

	@Test
	public void testThatProductionCostTypesAreDerivedFromFactories() {
		final List<String> typeIds = productionCostTypes.stream().map(ProductionCostType::getId).sorted()
				.collect(Collectors.toList());
		assertThat(typeIds, IsIterableContainingInOrder.contains("A", "B", "C"));
	}

	@Test
	public void testThatProductionItemTypesAreDerivedFromFactories() {
		final List<String> typeIds = productionItemTypes.stream().map(ProductionItemType::getId).sorted()
				.collect(Collectors.toList());
		assertThat(typeIds, IsIterableContainingInOrder.contains("D", "E", "F"));
	}

	@Configuration
	@Import({ ProductionTestConfiguration.WithoutPersistence.class })
	protected static class TestConfiguration {

		@Bean
		public ProductionCostTypeFactory aProductionCostTypeFactory() {
			return () -> Arrays.asList(mockCostType("A"), mockCostType("B"));
		}

		@Bean
		public ProductionCostTypeFactory anotherProductionCostTypeFactory() {
			return () -> Arrays.asList(mockCostType("C"));
		}

		private ProductionCostType mockCostType(final String id) {
			final ProductionCostType result = mock(ProductionCostType.class);
			when(result.getId()).thenReturn(id);
			return result;
		}

		@Bean
		public ProductionItemTypeFactory aProductionItemTypeFactory() {
			return () -> Arrays.asList(mockItemType("D"), mockItemType("E"));
		}

		@Bean
		public ProductionItemTypeFactory anotherProductionItemTypeFactory() {
			return () -> Arrays.asList(mockItemType("F"));
		}

		private ProductionItemType mockItemType(final String id) {
			final ProductionItemType result = mock(ProductionItemType.class);
			when(result.getId()).thenReturn(id);
			return result;
		}
	}
}