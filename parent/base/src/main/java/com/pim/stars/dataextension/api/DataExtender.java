package com.pim.stars.dataextension.api;

public interface DataExtender {

	public <T extends Entity<?>> T extendData(Entity<T> entity);
}