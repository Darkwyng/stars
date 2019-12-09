package com.pim.stars.mineral;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.mineral.api.policies.MineralType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MineralTestConfiguration.WithoutPersistence.class)
public class MineralConfigurationTest {

	@Autowired
	private List<MineralType> mineralTypes;

	@Test
	public void testThatApplicationContextStarts() {
	}

	@Test
	public void testThatMineralTypesAreCreated() {
		assertThat(mineralTypes, hasSize(3));
	}
}