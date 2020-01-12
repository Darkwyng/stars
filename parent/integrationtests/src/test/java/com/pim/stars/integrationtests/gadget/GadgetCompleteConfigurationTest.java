package com.pim.stars.integrationtests.gadget;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.gadget.GadgetConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GadgetConfiguration.Complete.class })
public class GadgetCompleteConfigurationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(applicationContext, not(nullValue()));
	}
}