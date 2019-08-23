package com.pim.stars.dataextension.imp;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.pim.stars.dataextension.api.DataExtensionPolicyProvider;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

public class DataExtensionPolicyProviderImp implements DataExtensionPolicyProvider {

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<?>, List<DataExtensionPolicy<?, ?>>> policyMap = null;

	@Override
	public <E extends Entity<?>> Collection<DataExtensionPolicy<E, ?>> getDataExtensionPoliciesForEntity(
			final E entity) {
		return getPoliciesForEntityClass(entity.getEntityClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <E extends Entity<?>> Collection<DataExtensionPolicy<E, ?>> getPoliciesForEntityClass(
			final Class<?> entityClass) {
		if (policyMap == null) {
			synchronized (this) {
				if (policyMap == null) {
					policyMap = applicationContext.getBeansOfType(DataExtensionPolicy.class).values().stream()
							.collect(Collectors.groupingBy(DataExtensionPolicy::getEntityClass));
				}
			}
		}
		return (Collection) policyMap.get(entityClass);
	}
}