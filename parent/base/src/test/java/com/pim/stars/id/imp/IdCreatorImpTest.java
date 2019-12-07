package com.pim.stars.id.imp;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

import org.junit.jupiter.api.Test;

import com.pim.stars.id.api.IdCreator;

public class IdCreatorImpTest {

	@Test
	public void testThatIdsAreCreated() {
		final IdCreator testee = new IdCreatorImp();
		assertThat(testee.createId(), not(emptyOrNullString()));
	}
}