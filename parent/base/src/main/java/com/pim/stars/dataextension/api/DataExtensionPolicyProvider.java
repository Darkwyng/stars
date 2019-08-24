package com.pim.stars.dataextension.api;

import java.util.Collection;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

public interface DataExtensionPolicyProvider {

	public <E extends Entity<?>> Collection<DataExtensionPolicy<E, ?>> getDataExtensionPoliciesForEntity(E entity);
}
