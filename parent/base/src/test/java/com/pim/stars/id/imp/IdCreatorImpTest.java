package com.pim.stars.id.imp;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.id.IdTestConfiguration;
import com.pim.stars.id.api.extensions.IdDataExtensionPolicy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IdCreatorImpTest.TestConfiguration.class)
public class IdCreatorImpTest {

	@Autowired
	private IdDataExtensionPolicy idDataExtensionPolicy;

	@Test
	public void testThatIdsAreCreated() {
		final String defaultId = idDataExtensionPolicy.getDefaultValue().get();
		assertThat(defaultId, not(isEmptyOrNullString()));
	}

	@Configuration
	protected static class TestConfiguration extends IdTestConfiguration {

		@Bean
		public IdDataExtensionPolicy idDataExtensionPolicy() {
			return new IdDataExtensionPolicy() {

				@Override
				public Class<? extends Entity> getEntityClass() {
					return null;
				}
			};
		}
	}
}