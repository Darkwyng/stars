package com.pim.stars.spring.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.ResolvableType;

import com.pim.stars.spring.imp.BeanLoaderImp.UnsatisfiedDependencyInChildException;

public class BeanLoaderImpTest {

	@Mock
	private AutowireCapableBeanFactory factory;

	private final BeanLoaderImp testee = new BeanLoaderImp();

	private final Object child = new Object() {

		@Override
		public String toString() {
			return "theChild";
		}

	};

	@BeforeEach
	private void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUnsatisfiedDependencyExceptionWithSpecialCauseIsEnhanced() {
		final ResolvableType type = mock(ResolvableType.class, Mockito.RETURNS_DEEP_STUBS);
		when(type.getType().getTypeName()).thenReturn("missingBeanName");

		final Throwable exception = new UnsatisfiedDependencyException("resourceDescription", "beanName",
				"injectionPoint", new NoSuchBeanDefinitionException(type));
		doThrow(exception).when(factory).autowireBean(Mockito.any());

		assertThrows(UnsatisfiedDependencyInChildException.class,
				() -> testee.autowireBeansForEffect(factory, "parentBeanName", child));
	}

	@Test
	public void testOtherUnsatisfiedDependencyExceptionIsReraised() {
		final Throwable exception = new UnsatisfiedDependencyException("resourceDescription", "beanName",
				"injectionPoint", new FatalBeanException("the message"));
		doThrow(exception).when(factory).autowireBean(Mockito.any());

		final UnsatisfiedDependencyException actualException = assertThrows(UnsatisfiedDependencyException.class,
				() -> testee.autowireBeansForEffect(factory, "parentBeanName", child));
		assertThat(actualException, is(exception));
	}

	@Test
	public void testOtherExceptionIsReraised() {
		final Throwable exception = new IllegalArgumentException("the message");
		doThrow(exception).when(factory).autowireBean(Mockito.any());

		final IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
				() -> testee.autowireBeansForEffect(factory, "parentBeanName", child));
		assertThat(actualException, is(exception));
	}
}
