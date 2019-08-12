package com.pim.stars.dataextension.imp;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

@SuppressWarnings("rawtypes") // Generics have been removed here. Maven will not compile with them even though Eclipse will.
public class DataExtenderImp implements DataExtender {

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<?>, List<DataExtensionPolicy>> policyMap = null;

	@Override
	public <T extends Entity, S extends T> void extendData(final S entity, final Class<T> entityClass) {

		final List<DataExtensionPolicy> policiesToUse = getPoliciesForEntityClass(entityClass);

		policiesToUse.stream().forEach(policy -> {

			final Optional<?> defaultValue = policy.getDefaultValue();

			if (defaultValue.isPresent()) {
				entity.set(policy.getKey(), defaultValue.get());
			}
		});
	}

	private List<DataExtensionPolicy> getPoliciesForEntityClass(final Class<?> entityClass) {
		if (policyMap == null) {
			synchronized (this) {
				if (policyMap == null) {
					policyMap = applicationContext.getBeansOfType(DataExtensionPolicy.class).values().stream()
							.collect(Collectors.groupingBy(DataExtensionPolicy::getEntityClass));
				}
			}
		}
		return policyMap.get(entityClass);
	}
}