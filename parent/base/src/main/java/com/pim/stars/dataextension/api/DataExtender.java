package com.pim.stars.dataextension.api;

public interface DataExtender {

	public <T extends Entity, S extends T> void extendData(S entity, Class<T> entityClass);
}