package com.pim.stars.mineral;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.mineral.api.policies.MineralType;
import com.pim.stars.mineral.imp.MineralTypeCreator;
import com.pim.stars.production.api.policies.ProductionCostType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MineralTestConfiguration.WithoutPersistence.class)
@ActiveProfiles("WithoutPersistence")
public class MineralConfigurationTest {

	@Autowired
	private Collection<MineralType> mineralTypes;
	@Autowired
	private Collection<CargoType> cargoTypes;
	@Autowired
	private Collection<ProductionCostType> productionCostTypes;

	@Test
	public void testThatMineralTypesAreCreated() {
		assertThat("Mineral types should be created by " + MineralTypeCreator.class.getSimpleName(), //
				mineralTypes, hasSize(3));
	}

	@Test
	public void testThatMineralTypesAreCreatedAsProductionCostTypes() {
		assertThat("Mineral types should be recognized as " + ProductionCostType.class.getSimpleName(), //
				productionCostTypes, containsInAnyOrder(mineralTypes.toArray()));
	}

	@Test
	public void testThatMineralTypesAreCreatedAsCargoTypes() {
		assertThat("Mineral types should be recognized as " + CargoType.class.getSimpleName(), //
				cargoTypes, containsInAnyOrder(mineralTypes.toArray()));
	}
}