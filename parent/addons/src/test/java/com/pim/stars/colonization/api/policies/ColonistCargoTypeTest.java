package com.pim.stars.colonization.api.policies;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.colonization.ColonizationTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ColonizationTestConfiguration.class)
public class ColonistCargoTypeTest {

	@Autowired
	private ColonistCargoType colonistCargoType;

	@Test
	void testThatIdIsNotEmpty() {
		assertThat(colonistCargoType.getId(), not(emptyOrNullString()));
	}
}
