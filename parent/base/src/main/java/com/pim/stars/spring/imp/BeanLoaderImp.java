package com.pim.stars.spring.imp;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.pim.stars.spring.api.BeanLoader;

@Component
public class BeanLoaderImp implements BeanLoader {

	@Autowired
	private ApplicationContext usualApplicationContext;

	/**
	 * This method loads an application context that will create beans depending on XML files used as input. The
	 * application context is closed again before the method returns, it is only used to instantiate objects.
	 */
	@Override
	public <T, R> Collection<R> loadFromXml(final Class<T> type, final List<String> locations,
			final BiFunction<String, T, R> mapper, final Function<T, Stream<? extends Object>> childBeanSupplier) {

		final AutowireCapableBeanFactory parentBeanFactory = usualApplicationContext.getAutowireCapableBeanFactory();

		try (ClassPathXmlApplicationContext temporaryApplicationContext = new ClassPathXmlApplicationContext(
				locations.toArray(new String[] {}), usualApplicationContext)) {

			return temporaryApplicationContext.getBeansOfType(type).entrySet().stream()
					.sorted(Comparator.comparing(Entry::getKey)) // sort so that result will always be the same
					.peek(entry -> {
						final String parentBeanName = entry.getKey();
						final T parentBean = entry.getValue();
						final Stream<? extends Object> childBeansToAutowire = childBeanSupplier.apply(parentBean);

						childBeansToAutowire
								.forEach(child -> autowireBeansForEffect(parentBeanFactory, parentBeanName, child));
					}).map(entry -> {
						final String beanName = entry.getKey();
						final T bean = entry.getValue();

						return mapper.apply(beanName, bean);
					}).collect(Collectors.toList());
		}
	}

	protected void autowireBeansForEffect(final AutowireCapableBeanFactory parentBeanFactory,
			final String parentBeanName, final Object child) {
		try {
			parentBeanFactory.autowireBean(child);
		} catch (final UnsatisfiedDependencyException e) {
			if (e.getCause() instanceof NoSuchBeanDefinitionException) {
				// Improve error message:
				final NoSuchBeanDefinitionException cause = (NoSuchBeanDefinitionException) e.getCause();
				final String missingBeanName = cause.getResolvableType().getType().getTypeName();
				throw new UnsatisfiedDependencyInChildException(parentBeanName, child.getClass(), missingBeanName, e);
			} else {
				throw e;
			}
		}
	}

	public static class UnsatisfiedDependencyInChildException extends BeanCreationException {

		private static final long serialVersionUID = -2153000048273845074L;

		public UnsatisfiedDependencyInChildException(final String parentBeanName, final Class<?> childClass,
				final String missingBeanName, final UnsatisfiedDependencyException cause) {
			super(MessageFormat.format(
					"''{0}'' contains ''{1}''," + " which requires the bean ''{2}'',"
							+ " which is missing in the application context.",
					parentBeanName, childClass.getName(), missingBeanName), cause);
		}
	}
}
