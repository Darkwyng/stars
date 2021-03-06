package com.pim.stars.id;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.id.api.IdCreator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IdTestConfiguration.WithoutPersistence.class)
@ActiveProfiles("WithoutPersistence")
public class IdConfigurationTest {

	@Autowired
	private IdCreator idCreator;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(idCreator, not(nullValue()));
	}
}
