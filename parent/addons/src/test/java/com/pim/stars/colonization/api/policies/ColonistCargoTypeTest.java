package com.pim.stars.colonization.api.policies;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.colonization.ColonizationTestConfiguration;
import com.pim.stars.colonization.api.ColonistCargoTypeProvider;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ColonizationTestConfiguration.WithoutPersistence.class)
@ActiveProfiles("WithoutPersistence")
public class ColonistCargoTypeTest {

	@Autowired
	private ColonistCargoTypeProvider colonistCargoTypeProvider;

	@Test
	void testThatIdIsNotEmpty() {
		assertThat(colonistCargoTypeProvider.getColonistCargoType().getId(), not(emptyOrNullString()));
	}
}
