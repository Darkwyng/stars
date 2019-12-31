package com.pim.stars.spring.api;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface BeanLoader {

	public <T, R> Collection<R> loadFromXml(final Class<T> type, final List<String> locations,
			final BiFunction<String, T, R> mapper, final Function<T, Stream<? extends Object>> childBeanSupplier);
}