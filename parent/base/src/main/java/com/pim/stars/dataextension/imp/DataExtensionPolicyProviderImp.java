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

// TODO: this can be fixed
@SuppressWarnings({ "rawtypes", "unchecked" }) // Generics have been removed here. Maven will not compile with them even though Eclipse will.
public class DataExtensionPolicyProviderImp implements DataExtensionPolicyProvider {

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<?>, List<DataExtensionPolicy>> policyMap = null;

	@Override
	public Collection<DataExtensionPolicy<?, ?>> getDataExtensionPoliciesForEntity(final Entity<?> entity) {
		return (Collection) getPoliciesForEntityClass(entity.getEntityClass());
	}

	private Collection<DataExtensionPolicy> getPoliciesForEntityClass(final Class<?> entityClass) {
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