package com.pim.stars.dataextension.imp;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.DataExtensionPolicyProvider;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

@Component
public class DataExtensionPolicyProviderImp implements DataExtensionPolicyProvider {

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<?>, List<DataExtensionPolicy<?, ?>>> policyMap = null;

	@Override
	public <E extends Entity<?>> Collection<DataExtensionPolicy<E, ?>> getDataExtensionPoliciesForEntity(
			final E entity) {
		return getPoliciesForEntityClass(entity.getEntityClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" }) // because getBeansOfType returns raw types and see below
	private <E extends Entity<?>> Collection<DataExtensionPolicy<E, ?>> getPoliciesForEntityClass(
			final Class<?> entityClass) {
		if (policyMap == null) {
			synchronized (this) {
				if (policyMap == null) {
					// Eclipse accepts this, but maven's compiler fails in "clean install" on parent, but not in "install" or in "clean install" on base:
					// policyMap = applicationContext.getBeansOfType(DataExtensionPolicy.class).values().stream()
					//			       .collect(Collectors.groupingBy(DataExtensionPolicy<?, ?>::getEntityClass));
					// Maven says:
					// Collector<        DataExtensionPolicy<?,?>,capture#2 of ?,Map<Class<?>,List<DataExtensionPolicy<?,?>>>>
					// cannot be converted to
					// Collector<? super DataExtensionPolicy     ,capture#2 of ?,Map<Class<?>,List<DataExtensionPolicy<?,?>>>>

					// So instead we do this:
					final Map map = applicationContext.getBeansOfType(DataExtensionPolicy.class).values().stream()
							.collect(Collectors.groupingBy(DataExtensionPolicy::getEntityClass));
					policyMap = map;
				}
			}
		}
		return (Collection) policyMap.get(entityClass);
	}
}