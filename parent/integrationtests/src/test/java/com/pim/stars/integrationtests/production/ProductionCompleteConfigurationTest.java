package com.pim.stars.integrationtests.production;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.persistence.testapi.PersistenceTestConfiguration;
import com.pim.stars.production.ProductionConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProductionConfiguration.Complete.class, PersistenceTestConfiguration.class })
public class ProductionCompleteConfigurationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(applicationContext, not(nullValue()));
	}
}