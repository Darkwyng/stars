package com.pim.stars.race.imp;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;
import com.pim.stars.race.imp.traits.AbstractRacialTrait;

public class RaceTraitProviderImp implements RaceTraitProvider {

	private Collection<PrimaryRacialTrait> primaryRacialTraits;
	private Collection<SecondaryRacialTrait> secondaryRacialTraits;

	@Autowired
	private RaceProperties raceProperties;
	@Autowired
	private ApplicationContext usualApplicationContext;

	@Override
	public Collection<PrimaryRacialTrait> getPrimaryRacialTraitCollection() {
		if (primaryRacialTraits == null) {
			primaryRacialTraits = loadFromXml(PrimaryRacialTrait.class,
					raceProperties.getPrimaryRacialTraitFilePaths());
		}
		return primaryRacialTraits;
	}

	@Override
	public Collection<SecondaryRacialTrait> getSecondaryRacialTraitCollection() {
		if (secondaryRacialTraits == null) {
			secondaryRacialTraits = loadFromXml(SecondaryRacialTrait.class,
					raceProperties.getSecondaryRacialTraitFilePaths());
		}
		return secondaryRacialTraits;
	}

	/**
	 * This method loads an application context that will create beans depending on XML files used as input. The
	 * application context is closed again before the method returns, it is only used to instantiate objects.
	 */
	private <T> Collection<T> loadFromXml(final Class<T> type, final List<String> locations) {
		final AutowireCapableBeanFactory parentBeanFactory = usualApplicationContext.getAutowireCapableBeanFactory();

		try (ClassPathXmlApplicationContext temporaryApplicationContext = new ClassPathXmlApplicationContext(
				locations.toArray(new String[] {}), usualApplicationContext)) {

			return temporaryApplicationContext.getBeansOfType(type).entrySet().stream().peek(entry -> {
				// Use the bean name as the trait ID, so that it does not have to be configured twice in the XML:
				final String beanName = entry.getKey();
				final AbstractRacialTrait bean = (AbstractRacialTrait) entry.getValue();
				bean.setId(beanName);

				// TODO: add better test for this:
				// Autowire beans of the usual context into the childrens' effects:
				bean.getEffectCollection().stream().forEach(parentBeanFactory::autowireBean);
			}).map(Entry<String, T>::getValue).collect(Collectors.toList());
		}
	}

	@Override
	public Optional<PrimaryRacialTrait> getPrimaryRacialTraitById(final String id) {
		return getPrimaryRacialTraitCollection().stream().filter(trait -> trait.getId().equals(id)).findAny();
	}

	@Override
	public Optional<SecondaryRacialTrait> getSecondaryRacialTraitById(final String id) {
		return getSecondaryRacialTraitCollection().stream().filter(trait -> trait.getId().equals(id)).findAny();
	}
}