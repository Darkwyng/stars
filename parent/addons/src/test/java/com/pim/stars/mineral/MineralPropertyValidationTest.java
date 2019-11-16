package com.pim.stars.mineral;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.pim.stars.mineral.imp.MineralProperties;

public class MineralPropertyValidationTest {

	@Nested
	public class TestFailingStartup {

		@Test
		public void testThatApplicationContextStartsWithDefaultPropertyValue() {
			final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
					MineralProperties.class);
			final MineralProperties bean = context.getBean("mineralProperties", MineralProperties.class);
			assertThat(bean.getFractionalMiningPrecision(), is(100)); // loaded from file "mineral.properties"
		}

		@Test
		public void testThatApplicationContextDoesNotStartWithInvalidProperties() {

			// TODO: this should raise an exception, because the validation of MineralProperties should fail.
			final ConfigurationPropertiesBindException exception = assertThrows(
					ConfigurationPropertiesBindException.class,
					() -> new AnnotationConfigApplicationContext(InvalidPropertySourceSetter.class,
							MineralProperties.class));
		}

		@Test
		public void testThatApplicationContextCanbeBuiltInSimpleScenario() {
			final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
					InvalidPropertySourceSetter.class);
			final Environment environment = context.getBean(Environment.class);

			// TODO: this is currently failing, because InvalidPropertySourceSetter is not considered.
			assertThat(environment.getProperty("this.example"), is("is.this"));
			assertThat(environment.getProperty("mineral.fractionalMiningPrecision"), is("0"));
		}

	}

	@ExtendWith(SpringExtension.class)
	@ContextConfiguration(classes = MineralTestConfiguration.class)
	@Nested
	public class TestAfterSuccessfulStartup {

		@Autowired
		private MineralProperties mineralProperties;
		@Autowired
		private SpringValidatorAdapter validator;

		@BeforeEach
		public void setUp() {
			mineralProperties.setFractionalMiningPrecision(1);
			mineralProperties.setBaseConcentration(1);
			mineralProperties.getDefaultSettings().setMineEfficiency(1);
			mineralProperties.getDefaultSettings().setMineProductionCost(1);
		}

		@Test
		public void testThatMineralPropertiesAreValidatedWithoutViolation() {
			assertValidationResult(validator, mineralProperties, emptyIterable());
		}

		@Test
		public void testThatFractionalMiningPrecisionIsValidated() {
			mineralProperties.setFractionalMiningPrecision(0);
			assertValidationResult(validator, mineralProperties, iterableWithSize(1));
		}

		@Test
		public void testThatCallingMethodDoesNotTriggerValidation() {
			assertThat(mineralProperties.getFractionalMiningPrecision(), is(1));
			mineralProperties.setFractionalMiningPrecision(0);
			assertThat(mineralProperties.getFractionalMiningPrecision(), is(0)); // no exception is raised
		}

		@Test
		public void testThatBaseConcentrationIsValidated() {
			mineralProperties.setBaseConcentration(0);
			assertValidationResult(validator, mineralProperties, iterableWithSize(1));
		}

		@Test
		public void testThatDefaultSettingsMineEfficiencyIsValidated() {
			mineralProperties.getDefaultSettings().setMineEfficiency(0);
			assertValidationResult(validator, mineralProperties, iterableWithSize(1));
		}

		@Test
		public void testThatDefaultSettingsMineProductionCostIsValidated() {
			mineralProperties.getDefaultSettings().setMineProductionCost(0);
			assertValidationResult(validator, mineralProperties, iterableWithSize(1));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void assertValidationResult(final Validator validator, final MineralProperties mineralProperties,
			final Matcher matcher) {
		final Set<ConstraintViolation<MineralProperties>> result = validator.validate(mineralProperties);
		final String actualViolations = result.stream()
				.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
				.collect(Collectors.joining(", "));
		assertThat(actualViolations, result, matcher);
	}
}