package com.pim.stars.turn.imp.policies.utilities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.turn.api.policies.GameEntityTransformer;

public class GameEntityTransformerMapper {

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<? extends Entity<?>>, List<GameEntityTransformer<Entity<?>, ?>>> transfomerMap = null;

	public List<GameEntityTransformer<Entity<?>, ?>> getTransformersForEntity(final Entity<?> gameEntity) {
		return getTransfomerMap().getOrDefault(gameEntity.getEntityClass(), Collections.emptyList());
	}

	@SuppressWarnings("rawtypes") // because getBeansOfType returns raw types
	private Map<Class<? extends Entity<?>>, List<GameEntityTransformer<Entity<?>, ?>>> getTransfomerMap() {
		if (transfomerMap == null) {
			synchronized (this) {
				if (transfomerMap == null) {
					final Collection<GameEntityTransformer> gameTransformerCollection = applicationContext
							.getBeansOfType(GameEntityTransformer.class).values();

					transfomerMap = gameTransformerCollection.stream()
							.collect(Collectors.groupingBy(GameEntityTransformer::getGameEntityClassToTransform));
				}
			}
		}
		return transfomerMap;
	}
}
