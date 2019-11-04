package com.pim.stars.turn.imp.policies.builder;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

@Component
public class DataExtensionPolicyLoader {

	@Autowired
	private ApplicationContext applicationContext;

	protected <E extends Entity<?>, T> DataExtensionPolicy<E, T> getSingleExtensionBean(
			final Class<? extends DataExtensionPolicy<E, T>> extensionToTransformClass) {
		final Map<String, ? extends DataExtensionPolicy<E, T>> candidates = applicationContext
				.getBeansOfType(extensionToTransformClass);

		if (candidates.isEmpty()) {
			throw new IllegalArgumentException("No beans could be found for the type " + extensionToTransformClass);

		} else if (candidates.size() != 1) {
			throw new IllegalArgumentException(
					"Too many beans could be found for the type " + extensionToTransformClass + ": "
							+ candidates.entrySet().stream()
									.map(entry -> entry.getKey() + "=" + entry.getValue().getClass())
									.collect(Collectors.joining(", ")));

		} else {
			return candidates.values().iterator().next();
		}
	}
}