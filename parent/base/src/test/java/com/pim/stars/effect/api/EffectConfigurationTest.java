package com.pim.stars.effect.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.effect.EffectTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EffectTestConfiguration.class)
public class EffectConfigurationTest {

	@Autowired
	private EffectProvider effectProvider;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(effectProvider, not(nullValue()));
	}
}
