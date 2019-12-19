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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.production.ProductionConfigurationTest.TestConfiguration;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class ProductionConfigurationTest {

	@Autowired
	private Collection<ProductionCostType> productionCostTypes;

	@Test
	public void testThatProductionCostTypesAreDerivedFromFactories() {
		final List<String> typeIds = productionCostTypes.stream().map(ProductionCostType::getId).sorted()
				.collect(Collectors.toList());
		assertThat(typeIds, IsIterableContainingInOrder.contains("A", "B", "C"));
	}

	@Configuration
	@Import({ ProductionTestConfiguration.class })
	protected static class TestConfiguration {

		@Bean
		public ProductionCostTypeFactory aProductionCostTypeFactory() {
			return () -> Arrays.asList(mockType("A"), mockType("B"));
		}

		@Bean
		public ProductionCostTypeFactory anotherProductionCostTypeFactory() {
			return () -> Arrays.asList(mockType("C"));
		}

		private ProductionCostType mockType(final String id) {
			final ProductionCostType result = mock(ProductionCostType.class);
			when(result.getId()).thenReturn(id);
			return result;
		}
	}
}