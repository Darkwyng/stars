package com.pim.stars.effect.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.effect.api.policies.EffectHolderProviderPolicy;
import com.pim.stars.effect.api.policies.EffectProviderPolicy;

public class EffectProviderImp implements EffectProvider {

	@Autowired(required = false)
	private final Collection<EffectHolderProviderPolicy> effectHolderProviderPolicyCollection = new ArrayList<>();
	@Autowired(required = false)
	private final Collection<EffectProviderPolicy<?>> effectProviderPolicyCollection = new ArrayList<>();

	@Override
	public <E extends Effect> Collection<E> getEffectCollection(final Object firstEffectHolder,
			final Class<E> effectClass) {

		// E.g. for a fleet find the race, all fleet components, ship designs and gadgets that might provide effects:
		final Set<Object> allEffectHolders = getAllEffectHolders(firstEffectHolder);
		// Collect the effects:
		final Set<E> allEffects = getAllEffects(allEffectHolders, effectClass);
		// Sort:
		final Collection<E> sortedEffects = sortEffectStream(allEffects.stream());

		return removeDeactivatedEffects(sortedEffects);
	}

	private Set<Object> getAllEffectHolders(final Object effectHolder) {
		final Set<Object> knownEffectHolders = new HashSet<>(Collections.singleton(effectHolder));

		Set<Object> newEffectHolders = knownEffectHolders;
		while (!newEffectHolders.isEmpty()) {
			newEffectHolders = getNewEffectHolders(newEffectHolders);
			knownEffectHolders.addAll(newEffectHolders);
		}
		return knownEffectHolders;
	}

	private Set<Object> getNewEffectHolders(final Set<Object> knownEffectHolders) {
		final Set<Object> foundEffectHolders = new HashSet<>();

		for (final Object effectHolder : knownEffectHolders) {
			effectHolderProviderPolicyCollection.stream() //
					.filter(policy -> policy.matchesInitialEffectHolder(effectHolder)) //
					.map(policy -> policy.getFurtherEffectHolders(effectHolder)).forEach(foundEffectHolders::addAll);
		}

		final Set<Object> newEffectHolders = foundEffectHolders.stream()
				.filter(candidate -> !knownEffectHolders.contains(candidate)).collect(Collectors.toSet());

		return newEffectHolders;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <E extends Effect> Set<E> getAllEffects(final Set<Object> allEffectHolders, final Class<E> effectClass) {
		final Set<E> allEffects = new HashSet<>();
		for (final Object effectHolder : allEffectHolders) {
			for (final EffectProviderPolicy policy : effectProviderPolicyCollection) {
				if (policy.matchesEffectHolder(effectHolder)) {
					allEffects.addAll(policy.getEffectCollectionFromEffectHolder(effectHolder, effectClass));
				}
			}
		}
		return allEffects;
	}

	private <E extends Effect> Collection<E> sortEffectStream(final Stream<E> stream) {
		final Comparator<Effect> first = Comparator.comparing(Effect::getSequence);
		final Comparator<Effect> second = Comparator.comparing(effect -> effect.getClass().getName());
		return stream.sorted(first.thenComparing(second)).collect(Collectors.toList());
	}

	private <E extends Effect> Collection<E> removeDeactivatedEffects(final Collection<E> sortedEffects) {
		final List<Class<? extends Effect>> deactivatedEffects = sortedEffects.stream()
				.map(effect -> effect.getDeactivatedEffects()).flatMap(Collection::stream).collect(Collectors.toList());

		if (!deactivatedEffects.isEmpty()) {
			return sortEffectStream(
					sortedEffects.stream().filter(effect -> !isDeactivated(deactivatedEffects, effect)));
		} else {
			return sortedEffects;
		}
	}

	private boolean isDeactivated(final List<Class<? extends Effect>> deactivatedEffects, final Effect effect) {
		return deactivatedEffects.stream().anyMatch(effectClass -> effectClass.equals(effect.getClass()));
	}
}