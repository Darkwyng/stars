package com.pim.stars.turn.imp.policies.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.turn.api.policies.TurnEntityCreator;

public class TurnEntityCreatorMapper {

	@Autowired(required = false)
	private final List<TurnEntityCreator<Entity<?>>> turnEntityCreatorCollection = new ArrayList<>();

	private Map<Class<? extends Entity<?>>, List<TurnEntityCreator<Entity<?>>>> creatorMap = null;

	public List<TurnEntityCreator<Entity<?>>> getCreatorForEntity(final Entity<?> gameEntity) {
		return getCreatorMap().get(gameEntity.getEntityClass());
	}

	private Map<Class<? extends Entity<?>>, List<TurnEntityCreator<Entity<?>>>> getCreatorMap() {
		if (creatorMap == null) {
			synchronized (this) {
				if (creatorMap == null) {
					creatorMap = turnEntityCreatorCollection.stream()
							.collect(Collectors.groupingBy(TurnEntityCreator::getEntityClass));
				}
			}
		}
		return creatorMap;
	}
}