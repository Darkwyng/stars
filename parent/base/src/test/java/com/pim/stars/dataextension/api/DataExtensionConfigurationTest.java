package com.pim.stars.dataextension.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.dataextension.DataExtensionConfiguration;
import com.pim.stars.dataextension.DataExtensionTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataExtensionTestConfiguration.class)
public class DataExtensionConfigurationTest {

	@Autowired
	private DataExtender dataExtender;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(dataExtender, not(nullValue()));
		new DataExtensionConfiguration.Complete(); // (for test coverage)
	}
}
