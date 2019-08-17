package com.pim.stars.dataextension.api;

import java.util.Collection;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

public interface DataExtensionPolicyProvider {

	public Collection<DataExtensionPolicy<?, ?>> getDataExtensionPoliciesForEntity(Entity<?> entity);
}
