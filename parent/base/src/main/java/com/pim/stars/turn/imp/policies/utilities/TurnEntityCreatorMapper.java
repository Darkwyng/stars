package com.pim.stars.turn.imp.policies.utilities;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.turn.api.policies.TurnEntityCreator;

@Component
public class TurnEntityCreatorMapper {

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<? extends Entity<?>>, List<TurnEntityCreator<Entity<?>>>> creatorMap = null;

	public List<TurnEntityCreator<Entity<?>>> getCreatorForEntity(final Entity<?> gameEntity) {
		return getCreatorMap().get(gameEntity.getEntityClass());
	}

	@SuppressWarnings("rawtypes") // because getBeansOfType returns raw types
	private Map<Class<? extends Entity<?>>, List<TurnEntityCreator<Entity<?>>>> getCreatorMap() {
		if (creatorMap == null) {
			synchronized (this) {
				if (creatorMap == null) {
					final Collection<TurnEntityCreator> turnEntityCreatorCollection = applicationContext
							.getBeansOfType(TurnEntityCreator.class).values();

					creatorMap = turnEntityCreatorCollection.stream()
							.collect(Collectors.groupingBy(TurnEntityCreator<Entity<?>>::getEntityClass));
				}
			}
		}
		return creatorMap;
	}
}