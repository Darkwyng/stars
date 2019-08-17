package com.pim.stars.dataextension.api;

public interface Entity<T extends Entity<?>> {

	public Class<T> getEntityClass();

	public Object get(String key);

	public void set(String key, Object value);
}
