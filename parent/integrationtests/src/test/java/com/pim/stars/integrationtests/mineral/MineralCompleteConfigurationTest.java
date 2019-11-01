package com.pim.stars.integrationtests.mineral;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.mineral.MineralConfiguration;
import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MineralConfiguration.Complete.class, PersistenceTestConfiguration.class })
public class MineralCompleteConfigurationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(applicationContext, not(nullValue()));
	}
}